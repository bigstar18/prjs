package org.hibernate.engine.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.QueryException;
import org.hibernate.ScrollableResults;
import org.hibernate.cfg.Settings;
import org.hibernate.engine.QueryParameters;
import org.hibernate.engine.RowSelection;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.event.EventSource;
import org.hibernate.hql.FilterTranslator;
import org.hibernate.hql.ParameterTranslations;
import org.hibernate.hql.QuerySplitter;
import org.hibernate.hql.QueryTranslator;
import org.hibernate.hql.QueryTranslatorFactory;
import org.hibernate.type.Type;
import org.hibernate.util.ArrayHelper;
import org.hibernate.util.EmptyIterator;
import org.hibernate.util.IdentitySet;
import org.hibernate.util.JoinedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HQLQueryPlan
  implements Serializable
{
  private static final Logger log = LoggerFactory.getLogger(HQLQueryPlan.class);
  private final String sourceQuery;
  private final QueryTranslator[] translators;
  private final String[] sqlStrings;
  private final ParameterMetadata parameterMetadata;
  private final ReturnMetadata returnMetadata;
  private final Set querySpaces;
  private final Set enabledFilterNames;
  private final boolean shallow;
  
  public HQLQueryPlan(String hql, boolean shallow, Map enabledFilters, SessionFactoryImplementor factory)
  {
    this(hql, null, shallow, enabledFilters, factory);
  }
  
  protected HQLQueryPlan(String hql, String collectionRole, boolean shallow, Map enabledFilters, SessionFactoryImplementor factory)
  {
    this.sourceQuery = hql;
    this.shallow = shallow;
    
    Set copy = new HashSet();
    copy.addAll(enabledFilters.keySet());
    this.enabledFilterNames = Collections.unmodifiableSet(copy);
    
    Set combinedQuerySpaces = new HashSet();
    String[] concreteQueryStrings = QuerySplitter.concreteQueries(hql, factory);
    int length = concreteQueryStrings.length;
    this.translators = new QueryTranslator[length];
    List sqlStringList = new ArrayList();
    for (int i = 0; i < length; i++)
    {
      if (collectionRole == null)
      {
        this.translators[i] = factory.getSettings()
          .getQueryTranslatorFactory()
          .createQueryTranslator(hql, concreteQueryStrings[i], enabledFilters, factory);
        this.translators[i].compile(factory.getSettings().getQuerySubstitutions(), shallow);
      }
      else
      {
        this.translators[i] = factory.getSettings()
          .getQueryTranslatorFactory()
          .createFilterTranslator(hql, concreteQueryStrings[i], enabledFilters, factory);
        ((FilterTranslator)this.translators[i]).compile(collectionRole, factory.getSettings().getQuerySubstitutions(), shallow);
      }
      combinedQuerySpaces.addAll(this.translators[i].getQuerySpaces());
      sqlStringList.addAll(this.translators[i].collectSqlStrings());
    }
    this.sqlStrings = ArrayHelper.toStringArray(sqlStringList);
    this.querySpaces = combinedQuerySpaces;
    if (length == 0)
    {
      this.parameterMetadata = new ParameterMetadata(null, null);
      this.returnMetadata = null;
    }
    else
    {
      this.parameterMetadata = buildParameterMetadata(this.translators[0].getParameterTranslations(), hql);
      if (this.translators[0].isManipulationStatement())
      {
        this.returnMetadata = null;
      }
      else if (length > 1)
      {
        int returns = this.translators[0].getReturnTypes().length;
        this.returnMetadata = new ReturnMetadata(this.translators[0].getReturnAliases(), new Type[returns]);
      }
      else
      {
        this.returnMetadata = new ReturnMetadata(this.translators[0].getReturnAliases(), this.translators[0].getReturnTypes());
      }
    }
  }
  
  public String getSourceQuery()
  {
    return this.sourceQuery;
  }
  
  public Set getQuerySpaces()
  {
    return this.querySpaces;
  }
  
  public ParameterMetadata getParameterMetadata()
  {
    return this.parameterMetadata;
  }
  
  public ReturnMetadata getReturnMetadata()
  {
    return this.returnMetadata;
  }
  
  public Set getEnabledFilterNames()
  {
    return this.enabledFilterNames;
  }
  
  public String[] getSqlStrings()
  {
    return this.sqlStrings;
  }
  
  public Set getUtilizedFilterNames()
  {
    return null;
  }
  
  public boolean isShallow()
  {
    return this.shallow;
  }
  
  public List performList(QueryParameters queryParameters, SessionImplementor session)
    throws HibernateException
  {
    if (log.isTraceEnabled())
    {
      log.trace("find: " + getSourceQuery());
      queryParameters.traceParameters(session.getFactory());
    }
    boolean hasLimit = (queryParameters.getRowSelection() != null) && 
      (queryParameters.getRowSelection().definesLimits());
    boolean needsLimit = (hasLimit) && (this.translators.length > 1);
    QueryParameters queryParametersToUse;
    QueryParameters queryParametersToUse;
    if (needsLimit)
    {
      log.warn("firstResult/maxResults specified on polymorphic query; applying in memory!");
      RowSelection selection = new RowSelection();
      selection.setFetchSize(queryParameters.getRowSelection().getFetchSize());
      selection.setTimeout(queryParameters.getRowSelection().getTimeout());
      queryParametersToUse = queryParameters.createCopyUsing(selection);
    }
    else
    {
      queryParametersToUse = queryParameters;
    }
    List combinedResults = new LinkedList();
    IdentitySet distinction = new IdentitySet();
    int includedCount = -1;
    for (int i = 0; i < this.translators.length; i++)
    {
      List tmp = this.translators[i].list(session, queryParametersToUse);
      if (needsLimit)
      {
        int first = queryParameters.getRowSelection().getFirstRow() == null ? 
          0 : 
          queryParameters.getRowSelection().getFirstRow().intValue();
        int max = queryParameters.getRowSelection().getMaxRows() == null ? 
          -1 : 
          queryParameters.getRowSelection().getMaxRows().intValue();
        int size = tmp.size();
        for (int x = 0; x < size; x++)
        {
          Object result = tmp.get(x);
          if (distinction.add(result))
          {
            includedCount++;
            if (includedCount >= first)
            {
              combinedResults.add(result);
              if ((max >= 0) && (includedCount > max)) {
                break;
              }
            }
          }
        }
      }
      else
      {
        combinedResults.addAll(tmp);
      }
    }
    return combinedResults;
  }
  
  public Iterator performIterate(QueryParameters queryParameters, EventSource session)
    throws HibernateException
  {
    if (log.isTraceEnabled())
    {
      log.trace("iterate: " + getSourceQuery());
      queryParameters.traceParameters(session.getFactory());
    }
    if (this.translators.length == 0) {
      return EmptyIterator.INSTANCE;
    }
    Iterator[] results = (Iterator[])null;
    boolean many = this.translators.length > 1;
    if (many) {
      results = new Iterator[this.translators.length];
    }
    Iterator result = null;
    for (int i = 0; i < this.translators.length; i++)
    {
      result = this.translators[i].iterate(queryParameters, session);
      if (many) {
        results[i] = result;
      }
    }
    return many ? new JoinedIterator(results) : result;
  }
  
  public ScrollableResults performScroll(QueryParameters queryParameters, SessionImplementor session)
    throws HibernateException
  {
    if (log.isTraceEnabled())
    {
      log.trace("iterate: " + getSourceQuery());
      queryParameters.traceParameters(session.getFactory());
    }
    if (this.translators.length != 1) {
      throw new QueryException("implicit polymorphism not supported for scroll() queries");
    }
    if ((queryParameters.getRowSelection().definesLimits()) && (this.translators[0].containsCollectionFetches())) {
      throw new QueryException("firstResult/maxResults not supported in conjunction with scroll() of a query containing collection fetches");
    }
    return this.translators[0].scroll(queryParameters, session);
  }
  
  public int performExecuteUpdate(QueryParameters queryParameters, SessionImplementor session)
    throws HibernateException
  {
    if (log.isTraceEnabled())
    {
      log.trace("executeUpdate: " + getSourceQuery());
      queryParameters.traceParameters(session.getFactory());
    }
    if (this.translators.length != 1) {
      log.warn("manipulation query [" + getSourceQuery() + "] resulted in [" + this.translators.length + "] split queries");
    }
    int result = 0;
    for (int i = 0; i < this.translators.length; i++) {
      result += this.translators[i].executeUpdate(queryParameters, session);
    }
    return result;
  }
  
  private ParameterMetadata buildParameterMetadata(ParameterTranslations parameterTranslations, String hql)
  {
    long start = System.currentTimeMillis();
    ParamLocationRecognizer recognizer = ParamLocationRecognizer.parseLocations(hql);
    long end = System.currentTimeMillis();
    if (log.isTraceEnabled()) {
      log.trace("HQL param location recognition took " + (end - start) + " mills (" + hql + ")");
    }
    int ordinalParamCount = parameterTranslations.getOrdinalParameterCount();
    int[] locations = ArrayHelper.toIntArray(recognizer.getOrdinalParameterLocationList());
    if ((parameterTranslations.supportsOrdinalParameterMetadata()) && (locations.length != ordinalParamCount)) {
      throw new HibernateException("ordinal parameter mismatch");
    }
    ordinalParamCount = locations.length;
    OrdinalParameterDescriptor[] ordinalParamDescriptors = new OrdinalParameterDescriptor[ordinalParamCount];
    for (int i = 1; i <= ordinalParamCount; i++) {
      ordinalParamDescriptors[(i - 1)] = new OrdinalParameterDescriptor(
        i, 
        parameterTranslations.supportsOrdinalParameterMetadata() ? 
        parameterTranslations.getOrdinalParameterExpectedType(i) : 
        null, 
        locations[(i - 1)]);
    }
    Iterator itr = recognizer.getNamedParameterDescriptionMap().entrySet().iterator();
    Map namedParamDescriptorMap = new HashMap();
    while (itr.hasNext())
    {
      Map.Entry entry = (Map.Entry)itr.next();
      String name = (String)entry.getKey();
      ParamLocationRecognizer.NamedParameterDescription description = 
        (ParamLocationRecognizer.NamedParameterDescription)entry.getValue();
      namedParamDescriptorMap.put(
        name, 
        new NamedParameterDescriptor(
        name, 
        parameterTranslations.getNamedParameterExpectedType(name), 
        description.buildPositionsArray(), 
        description.isJpaStyle()));
    }
    return new ParameterMetadata(ordinalParamDescriptors, namedParamDescriptorMap);
  }
  
  public QueryTranslator[] getTranslators()
  {
    QueryTranslator[] copy = new QueryTranslator[this.translators.length];
    System.arraycopy(this.translators, 0, copy, 0, copy.length);
    return copy;
  }
}
