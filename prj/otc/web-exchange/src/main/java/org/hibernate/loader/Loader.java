package org.hibernate.loader;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.hibernate.AssertionFailure;
import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.QueryException;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.StaleObjectStateException;
import org.hibernate.WrongClassException;
import org.hibernate.cache.FilterKey;
import org.hibernate.cache.QueryCache;
import org.hibernate.cache.QueryKey;
import org.hibernate.cache.QueryResultsRegion;
import org.hibernate.cfg.Settings;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.BatchFetchQueue;
import org.hibernate.engine.EntityEntry;
import org.hibernate.engine.EntityKey;
import org.hibernate.engine.EntityUniqueKey;
import org.hibernate.engine.LoadQueryInfluencers;
import org.hibernate.engine.PersistenceContext;
import org.hibernate.engine.QueryParameters;
import org.hibernate.engine.RowSelection;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.engine.SubselectFetch;
import org.hibernate.engine.TwoPhaseLoad;
import org.hibernate.engine.TypedValue;
import org.hibernate.engine.jdbc.ColumnNameCache;
import org.hibernate.engine.jdbc.JdbcSupport;
import org.hibernate.engine.loading.CollectionLoadContext;
import org.hibernate.engine.loading.LoadContexts;
import org.hibernate.event.EventSource;
import org.hibernate.event.PostLoadEvent;
import org.hibernate.event.PreLoadEvent;
import org.hibernate.exception.JDBCExceptionHelper;
import org.hibernate.hql.HolderInstantiator;
import org.hibernate.impl.FetchingScrollableResultsImpl;
import org.hibernate.impl.ScrollableResultsImpl;
import org.hibernate.jdbc.Batcher;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.entity.Loadable;
import org.hibernate.persister.entity.UniqueKeyLoadable;
import org.hibernate.pretty.MessageHelper;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.hibernate.stat.Statistics;
import org.hibernate.stat.StatisticsImplementor;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.type.AssociationType;
import org.hibernate.type.CollectionType;
import org.hibernate.type.EntityType;
import org.hibernate.type.Type;
import org.hibernate.type.VersionType;
import org.hibernate.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Loader
{
  private static final Logger log = LoggerFactory.getLogger(Loader.class);
  private final SessionFactoryImplementor factory;
  private ColumnNameCache columnNameCache;
  
  public Loader(SessionFactoryImplementor factory)
  {
    this.factory = factory;
  }
  
  protected abstract String getSQLString();
  
  protected abstract Loadable[] getEntityPersisters();
  
  protected boolean[] getEntityEagerPropertyFetches()
  {
    return null;
  }
  
  protected int[] getOwners()
  {
    return null;
  }
  
  protected EntityType[] getOwnerAssociationTypes()
  {
    return null;
  }
  
  protected CollectionPersister[] getCollectionPersisters()
  {
    return null;
  }
  
  protected int[] getCollectionOwners()
  {
    return null;
  }
  
  protected abstract LockMode[] getLockModes(LockOptions paramLockOptions);
  
  protected String applyLocks(String sql, LockOptions lockOptions, Dialect dialect)
    throws HibernateException
  {
    return sql;
  }
  
  protected boolean upgradeLocks()
  {
    return false;
  }
  
  protected boolean isSingleRowLoader()
  {
    return false;
  }
  
  protected String[] getAliases()
  {
    return null;
  }
  
  protected String preprocessSQL(String sql, QueryParameters parameters, Dialect dialect)
    throws HibernateException
  {
    sql = applyLocks(sql, parameters.getLockOptions(), dialect);
    
    return getFactory().getSettings().isCommentsEnabled() ? 
      prependComment(sql, parameters) : sql;
  }
  
  private String prependComment(String sql, QueryParameters parameters)
  {
    String comment = parameters.getComment();
    if (comment == null) {
      return sql;
    }
    return 
    


      comment.length() + sql.length() + 5 + "/* " + comment + " */ " + sql;
  }
  
  private List doQueryAndInitializeNonLazyCollections(SessionImplementor session, QueryParameters queryParameters, boolean returnProxies)
    throws HibernateException, SQLException
  {
    PersistenceContext persistenceContext = session.getPersistenceContext();
    boolean defaultReadOnlyOrig = persistenceContext.isDefaultReadOnly();
    if (queryParameters.isReadOnlyInitialized()) {
      persistenceContext.setDefaultReadOnly(queryParameters.isReadOnly());
    } else {
      queryParameters.setReadOnly(persistenceContext.isDefaultReadOnly());
    }
    persistenceContext.beforeLoad();
    List result;
    try
    {
      List result;
      try
      {
        result = doQuery(session, queryParameters, returnProxies);
      }
      finally
      {
        List result;
        persistenceContext.afterLoad();
      }
      persistenceContext.initializeNonLazyCollections();
    }
    finally
    {
      persistenceContext.setDefaultReadOnly(defaultReadOnlyOrig);
    }
    return result;
  }
  
  public Object loadSingleRow(ResultSet resultSet, SessionImplementor session, QueryParameters queryParameters, boolean returnProxies)
    throws HibernateException
  {
    int entitySpan = getEntityPersisters().length;
    List hydratedObjects = entitySpan == 0 ? 
      null : new ArrayList(entitySpan);
    try
    {
      result = getRowFromResultSet(
        resultSet, 
        session, 
        queryParameters, 
        getLockModes(queryParameters.getLockOptions()), 
        null, 
        hydratedObjects, 
        new EntityKey[entitySpan], 
        returnProxies);
    }
    catch (SQLException sqle)
    {
      Object result;
      throw JDBCExceptionHelper.convert(
        this.factory.getSQLExceptionConverter(), 
        sqle, 
        "could not read next row of results", 
        getSQLString());
    }
    Object result;
    initializeEntitiesAndCollections(
      hydratedObjects, 
      resultSet, 
      session, 
      queryParameters.isReadOnly(session));
    
    session.getPersistenceContext().initializeNonLazyCollections();
    return result;
  }
  
  private Object sequentialLoad(ResultSet resultSet, SessionImplementor session, QueryParameters queryParameters, boolean returnProxies, EntityKey keyToRead)
    throws HibernateException
  {
    int entitySpan = getEntityPersisters().length;
    List hydratedObjects = entitySpan == 0 ? 
      null : new ArrayList(entitySpan);
    
    Object result = null;
    EntityKey[] loadedKeys = new EntityKey[entitySpan];
    try
    {
      do
      {
        Object loaded = getRowFromResultSet(
          resultSet, 
          session, 
          queryParameters, 
          getLockModes(queryParameters.getLockOptions()), 
          null, 
          hydratedObjects, 
          loadedKeys, 
          returnProxies);
        if (result == null) {
          result = loaded;
        }
        if (!keyToRead.equals(loadedKeys[0])) {
          break;
        }
      } while (
      













        resultSet.next());
    }
    catch (SQLException sqle)
    {
      throw JDBCExceptionHelper.convert(
        this.factory.getSQLExceptionConverter(), 
        sqle, 
        "could not doAfterTransactionCompletion sequential read of results (forward)", 
        getSQLString());
    }
    initializeEntitiesAndCollections(
      hydratedObjects, 
      resultSet, 
      session, 
      queryParameters.isReadOnly(session));
    
    session.getPersistenceContext().initializeNonLazyCollections();
    return result;
  }
  
  public Object loadSequentialRowsForward(ResultSet resultSet, SessionImplementor session, QueryParameters queryParameters, boolean returnProxies)
    throws HibernateException
  {
    try
    {
      if (resultSet.isAfterLast()) {
        return null;
      }
      if (resultSet.isBeforeFirst()) {
        resultSet.next();
      }
      EntityKey currentKey = getKeyFromResultSet(
        0, 
        getEntityPersisters()[0], 
        null, 
        resultSet, 
        session);
      

      return sequentialLoad(resultSet, session, queryParameters, returnProxies, currentKey);
    }
    catch (SQLException sqle)
    {
      throw JDBCExceptionHelper.convert(
        this.factory.getSQLExceptionConverter(), 
        sqle, 
        "could not doAfterTransactionCompletion sequential read of results (forward)", 
        getSQLString());
    }
  }
  
  public Object loadSequentialRowsReverse(ResultSet resultSet, SessionImplementor session, QueryParameters queryParameters, boolean returnProxies, boolean isLogicallyAfterLast)
    throws HibernateException
  {
    try
    {
      if (resultSet.isFirst()) {
        return null;
      }
      EntityKey keyToRead = null;
      if ((resultSet.isAfterLast()) && (isLogicallyAfterLast))
      {
        resultSet.last();
        keyToRead = getKeyFromResultSet(
          0, 
          getEntityPersisters()[0], 
          null, 
          resultSet, 
          session);
      }
      else
      {
        resultSet.previous();
        




        boolean firstPass = true;
        EntityKey lastKey = getKeyFromResultSet(
          0, 
          getEntityPersisters()[0], 
          null, 
          resultSet, 
          session);
        while (resultSet.previous())
        {
          EntityKey checkKey = getKeyFromResultSet(
            0, 
            getEntityPersisters()[0], 
            null, 
            resultSet, 
            session);
          if (firstPass)
          {
            firstPass = false;
            keyToRead = checkKey;
          }
          if (!lastKey.equals(checkKey)) {
            break;
          }
        }
      }
      while (resultSet.previous())
      {
        EntityKey checkKey = getKeyFromResultSet(
          0, 
          getEntityPersisters()[0], 
          null, 
          resultSet, 
          session);
        if (!keyToRead.equals(checkKey)) {
          break;
        }
      }
      resultSet.next();
      

      return sequentialLoad(resultSet, session, queryParameters, returnProxies, keyToRead);
    }
    catch (SQLException sqle)
    {
      throw JDBCExceptionHelper.convert(
        this.factory.getSQLExceptionConverter(), 
        sqle, 
        "could not doAfterTransactionCompletion sequential read of results (forward)", 
        getSQLString());
    }
  }
  
  private static EntityKey getOptionalObjectKey(QueryParameters queryParameters, SessionImplementor session)
  {
    Object optionalObject = queryParameters.getOptionalObject();
    Serializable optionalId = queryParameters.getOptionalId();
    String optionalEntityName = queryParameters.getOptionalEntityName();
    if ((optionalObject != null) && (optionalEntityName != null)) {
      return new EntityKey(
        optionalId, 
        session.getEntityPersister(optionalEntityName, optionalObject), 
        session.getEntityMode());
    }
    return null;
  }
  
  private Object getRowFromResultSet(ResultSet resultSet, SessionImplementor session, QueryParameters queryParameters, LockMode[] lockModesArray, EntityKey optionalObjectKey, List hydratedObjects, EntityKey[] keys, boolean returnProxies)
    throws SQLException, HibernateException
  {
    Loadable[] persisters = getEntityPersisters();
    int entitySpan = persisters.length;
    for (int i = 0; i < entitySpan; i++) {
      keys[i] = getKeyFromResultSet(
        i, 
        persisters[i], 
        i == entitySpan - 1 ? 
        queryParameters.getOptionalId() : 
        null, 
        resultSet, 
        session);
    }
    registerNonExists(keys, persisters, session);
    

    Object[] row = getRow(
      resultSet, 
      persisters, 
      keys, 
      queryParameters.getOptionalObject(), 
      optionalObjectKey, 
      lockModesArray, 
      hydratedObjects, 
      session);
    

    readCollectionElements(row, resultSet, session);
    if (returnProxies) {
      for (int i = 0; i < entitySpan; i++)
      {
        Object entity = row[i];
        Object proxy = session.getPersistenceContext().proxyFor(persisters[i], keys[i], entity);
        if (entity != proxy)
        {
          ((HibernateProxy)proxy).getHibernateLazyInitializer().setImplementation(entity);
          row[i] = proxy;
        }
      }
    }
    applyPostLoadLocks(row, lockModesArray, session);
    
    return getResultColumnOrRow(row, queryParameters.getResultTransformer(), resultSet, session);
  }
  
  protected void applyPostLoadLocks(Object[] row, LockMode[] lockModesArray, SessionImplementor session) {}
  
  private void readCollectionElements(Object[] row, ResultSet resultSet, SessionImplementor session)
    throws SQLException, HibernateException
  {
    CollectionPersister[] collectionPersisters = getCollectionPersisters();
    if (collectionPersisters != null)
    {
      CollectionAliases[] descriptors = getCollectionAliases();
      int[] collectionOwners = getCollectionOwners();
      for (int i = 0; i < collectionPersisters.length; i++)
      {
        boolean hasCollectionOwners = (collectionOwners != null) && 
          (collectionOwners[i] > -1);
        


        Object owner = hasCollectionOwners ? 
          row[collectionOwners[i]] : 
          null;
        
        CollectionPersister collectionPersister = collectionPersisters[i];
        Serializable key;
        Serializable key;
        if (owner == null) {
          key = null;
        } else {
          key = collectionPersister.getCollectionType().getKeyOfOwner(owner, session);
        }
        readCollectionElement(
          owner, 
          key, 
          collectionPersister, 
          descriptors[i], 
          resultSet, 
          session);
      }
    }
  }
  
  private List doQuery(SessionImplementor session, QueryParameters queryParameters, boolean returnProxies)
    throws SQLException, HibernateException
  {
    RowSelection selection = queryParameters.getRowSelection();
    int maxRows = hasMaxRows(selection) ? 
      selection.getMaxRows().intValue() : 
      2147483647;
    
    int entitySpan = getEntityPersisters().length;
    
    ArrayList hydratedObjects = entitySpan == 0 ? null : new ArrayList(entitySpan * 10);
    PreparedStatement st = prepareQueryStatement(queryParameters, false, session);
    ResultSet rs = getResultSet(st, queryParameters.hasAutoDiscoverScalarTypes(), queryParameters.isCallable(), selection, session);
    






    EntityKey optionalObjectKey = getOptionalObjectKey(queryParameters, session);
    LockMode[] lockModesArray = getLockModes(queryParameters.getLockOptions());
    boolean createSubselects = isSubselectLoadingEnabled();
    List subselectResultKeys = createSubselects ? new ArrayList() : null;
    List results = new ArrayList();
    try
    {
      handleEmptyCollections(queryParameters.getCollectionKeys(), rs, session);
      
      EntityKey[] keys = new EntityKey[entitySpan];
      if (log.isTraceEnabled()) {
        log.trace("processing result set");
      }
      for (int count = 0; (count < maxRows) && (rs.next()); count++)
      {
        if (log.isTraceEnabled()) {
          log.debug("result set row: " + count);
        }
        Object result = getRowFromResultSet(
          rs, 
          session, 
          queryParameters, 
          lockModesArray, 
          optionalObjectKey, 
          hydratedObjects, 
          keys, 
          returnProxies);
        
        results.add(result);
        if (createSubselects)
        {
          subselectResultKeys.add(keys);
          keys = new EntityKey[entitySpan];
        }
      }
      if (log.isTraceEnabled()) {
        log.trace("done processing result set (" + count + " rows)");
      }
    }
    finally
    {
      session.getBatcher().closeQueryStatement(st, rs);
    }
    initializeEntitiesAndCollections(hydratedObjects, rs, session, queryParameters.isReadOnly(session));
    if (createSubselects) {
      createSubselects(subselectResultKeys, queryParameters, session);
    }
    return results;
  }
  
  protected boolean isSubselectLoadingEnabled()
  {
    return false;
  }
  
  protected boolean hasSubselectLoadableCollections()
  {
    Loadable[] loadables = getEntityPersisters();
    for (int i = 0; i < loadables.length; i++) {
      if (loadables[i].hasSubselectLoadableCollections()) {
        return true;
      }
    }
    return false;
  }
  
  private static Set[] transpose(List keys)
  {
    Set[] result = new Set[((EntityKey[])keys.get(0)).length];
    for (int j = 0; j < result.length; j++)
    {
      result[j] = new HashSet(keys.size());
      for (int i = 0; i < keys.size(); i++) {
        result[j].add(((EntityKey[])keys.get(i))[j]);
      }
    }
    return result;
  }
  
  private void createSubselects(List keys, QueryParameters queryParameters, SessionImplementor session)
  {
    if (keys.size() > 1)
    {
      Set[] keySets = transpose(keys);
      
      Map namedParameterLocMap = buildNamedParameterLocMap(queryParameters);
      
      Loadable[] loadables = getEntityPersisters();
      String[] aliases = getAliases();
      Iterator iter = keys.iterator();
      EntityKey[] rowKeys;
      int i;
      for (; iter.hasNext(); i < rowKeys.length)
      {
        rowKeys = (EntityKey[])iter.next();
        i = 0; continue;
        if ((rowKeys[i] != null) && (loadables[i].hasSubselectLoadableCollections()))
        {
          SubselectFetch subselectFetch = new SubselectFetch(
          
            aliases[i], 
            loadables[i], 
            queryParameters, 
            keySets[i], 
            namedParameterLocMap);
          

          session.getPersistenceContext()
            .getBatchFetchQueue()
            .addSubselect(rowKeys[i], subselectFetch);
        }
        i++;
      }
    }
  }
  
  private Map buildNamedParameterLocMap(QueryParameters queryParameters)
  {
    if (queryParameters.getNamedParameters() != null)
    {
      Map namedParameterLocMap = new HashMap();
      Iterator piter = queryParameters.getNamedParameters().keySet().iterator();
      while (piter.hasNext())
      {
        String name = (String)piter.next();
        namedParameterLocMap.put(
          name, 
          getNamedParameterLocs(name));
      }
      return namedParameterLocMap;
    }
    return null;
  }
  
  private void initializeEntitiesAndCollections(List hydratedObjects, Object resultSetId, SessionImplementor session, boolean readOnly)
    throws HibernateException
  {
    CollectionPersister[] collectionPersisters = getCollectionPersisters();
    if (collectionPersisters != null) {
      for (int i = 0; i < collectionPersisters.length; i++) {
        if (collectionPersisters[i].isArray()) {
          endCollectionLoad(resultSetId, session, collectionPersisters[i]);
        }
      }
    }
    PostLoadEvent post;
    PreLoadEvent pre;
    PostLoadEvent post;
    if (session.isEventSource())
    {
      PreLoadEvent pre = new PreLoadEvent((EventSource)session);
      post = new PostLoadEvent((EventSource)session);
    }
    else
    {
      pre = null;
      post = null;
    }
    if (hydratedObjects != null)
    {
      int hydratedObjectsSize = hydratedObjects.size();
      if (log.isTraceEnabled()) {
        log.trace("total objects hydrated: " + hydratedObjectsSize);
      }
      for (int i = 0; i < hydratedObjectsSize; i++) {
        TwoPhaseLoad.initializeEntity(hydratedObjects.get(i), readOnly, session, pre, post);
      }
    }
    if (collectionPersisters != null) {
      for (int i = 0; i < collectionPersisters.length; i++) {
        if (!collectionPersisters[i].isArray()) {
          endCollectionLoad(resultSetId, session, collectionPersisters[i]);
        }
      }
    }
  }
  
  private void endCollectionLoad(Object resultSetId, SessionImplementor session, CollectionPersister collectionPersister)
  {
    session.getPersistenceContext().getLoadContexts().getCollectionLoadContext((ResultSet)resultSetId).endLoadingCollections(collectionPersister);
  }
  
  protected List getResultList(List results, ResultTransformer resultTransformer)
    throws QueryException
  {
    return results;
  }
  
  protected Object getResultColumnOrRow(Object[] row, ResultTransformer transformer, ResultSet rs, SessionImplementor session)
    throws SQLException, HibernateException
  {
    return row;
  }
  
  private void registerNonExists(EntityKey[] keys, Loadable[] persisters, SessionImplementor session)
  {
    int[] owners = getOwners();
    if (owners != null)
    {
      EntityType[] ownerAssociationTypes = getOwnerAssociationTypes();
      for (int i = 0; i < keys.length; i++)
      {
        int owner = owners[i];
        if (owner > -1)
        {
          EntityKey ownerKey = keys[owner];
          if ((keys[i] == null) && (ownerKey != null))
          {
            PersistenceContext persistenceContext = session.getPersistenceContext();
            


















            boolean isOneToOneAssociation = (ownerAssociationTypes != null) && 
              (ownerAssociationTypes[i] != null) && 
              (ownerAssociationTypes[i].isOneToOne());
            if (isOneToOneAssociation) {
              persistenceContext.addNullProperty(ownerKey, 
                ownerAssociationTypes[i].getPropertyName());
            }
          }
        }
      }
    }
  }
  
  private void readCollectionElement(Object optionalOwner, Serializable optionalKey, CollectionPersister persister, CollectionAliases descriptor, ResultSet rs, SessionImplementor session)
    throws HibernateException, SQLException
  {
    PersistenceContext persistenceContext = session.getPersistenceContext();
    
    Serializable collectionRowKey = (Serializable)persister.readKey(
      rs, 
      descriptor.getSuffixedKeyAliases(), 
      session);
    if (collectionRowKey != null)
    {
      if (log.isDebugEnabled()) {
        log.debug(
          "found row of collection: " + 
          MessageHelper.collectionInfoString(persister, collectionRowKey, getFactory()));
      }
      Object owner = optionalOwner;
      if (owner == null) {
        owner = persistenceContext.getCollectionOwner(collectionRowKey, persister);
      }
      PersistentCollection rowCollection = persistenceContext.getLoadContexts()
        .getCollectionLoadContext(rs)
        .getLoadingCollection(persister, collectionRowKey);
      if (rowCollection != null) {
        rowCollection.readFrom(rs, persister, descriptor, owner);
      }
    }
    else if (optionalKey != null)
    {
      if (log.isDebugEnabled()) {
        log.debug(
          "result set contains (possibly empty) collection: " + 
          MessageHelper.collectionInfoString(persister, optionalKey, getFactory()));
      }
      persistenceContext.getLoadContexts().getCollectionLoadContext(rs).getLoadingCollection(persister, optionalKey);
    }
  }
  
  private void handleEmptyCollections(Serializable[] keys, Object resultSetId, SessionImplementor session)
  {
    if (keys != null)
    {
      CollectionPersister[] collectionPersisters = getCollectionPersisters();
      for (int j = 0; j < collectionPersisters.length; j++) {
        for (int i = 0; i < keys.length; i++)
        {
          if (log.isDebugEnabled()) {
            log.debug(
              "result set contains (possibly empty) collection: " + 
              MessageHelper.collectionInfoString(collectionPersisters[j], keys[i], getFactory()));
          }
          session.getPersistenceContext().getLoadContexts().getCollectionLoadContext((ResultSet)resultSetId).getLoadingCollection(collectionPersisters[j], keys[i]);
        }
      }
    }
  }
  
  private EntityKey getKeyFromResultSet(int i, Loadable persister, Serializable id, ResultSet rs, SessionImplementor session)
    throws HibernateException, SQLException
  {
    Serializable resultId;
    Serializable resultId;
    if ((isSingleRowLoader()) && (id != null))
    {
      resultId = id;
    }
    else
    {
      Type idType = persister.getIdentifierType();
      resultId = (Serializable)idType.nullSafeGet(
        rs, 
        getEntityAliases()[i].getSuffixedKeyAliases(), 
        session, 
        null);
      

      boolean idIsResultId = (id != null) && 
        (resultId != null) && 
        (idType.isEqual(id, resultId, session.getEntityMode(), this.factory));
      if (idIsResultId) {
        resultId = id;
      }
    }
    return resultId == null ? 
      null : 
      new EntityKey(resultId, persister, session.getEntityMode());
  }
  
  private void checkVersion(int i, Loadable persister, Serializable id, Object entity, ResultSet rs, SessionImplementor session)
    throws HibernateException, SQLException
  {
    Object version = session.getPersistenceContext().getEntry(entity).getVersion();
    if (version != null)
    {
      VersionType versionType = persister.getVersionType();
      Object currentVersion = versionType.nullSafeGet(
        rs, 
        getEntityAliases()[i].getSuffixedVersionAliases(), 
        session, 
        null);
      if (!versionType.isEqual(version, currentVersion))
      {
        if (session.getFactory().getStatistics().isStatisticsEnabled()) {
          session.getFactory().getStatisticsImplementor().optimisticFailure(persister.getEntityName());
        }
        throw new StaleObjectStateException(persister.getEntityName(), id);
      }
    }
  }
  
  private Object[] getRow(ResultSet rs, Loadable[] persisters, EntityKey[] keys, Object optionalObject, EntityKey optionalObjectKey, LockMode[] lockModes, List hydratedObjects, SessionImplementor session)
    throws HibernateException, SQLException
  {
    int cols = persisters.length;
    EntityAliases[] descriptors = getEntityAliases();
    if (log.isDebugEnabled()) {
      log.debug(
        "result row: " + 
        StringHelper.toString(keys));
    }
    Object[] rowResults = new Object[cols];
    for (int i = 0; i < cols; i++)
    {
      Object object = null;
      EntityKey key = keys[i];
      if (keys[i] != null)
      {
        object = session.getEntityUsingInterceptor(key);
        if (object != null) {
          instanceAlreadyLoaded(
            rs, 
            i, 
            persisters[i], 
            key, 
            object, 
            lockModes[i], 
            session);
        } else {
          object = instanceNotYetLoaded(
            rs, 
            i, 
            persisters[i], 
            descriptors[i].getRowIdAlias(), 
            key, 
            lockModes[i], 
            optionalObjectKey, 
            optionalObject, 
            hydratedObjects, 
            session);
        }
      }
      rowResults[i] = object;
    }
    return rowResults;
  }
  
  private void instanceAlreadyLoaded(ResultSet rs, int i, Loadable persister, EntityKey key, Object object, LockMode lockMode, SessionImplementor session)
    throws HibernateException, SQLException
  {
    if (!persister.isInstance(object, session.getEntityMode())) {
      throw new WrongClassException(
        "loaded object was of wrong class " + object.getClass(), 
        key.getIdentifier(), 
        persister.getEntityName());
    }
    if ((LockMode.NONE != lockMode) && (upgradeLocks()))
    {
      if (persister.isVersioned()) {}
      boolean isVersionCheckNeeded = 
        session.getPersistenceContext().getEntry(object)
        .getLockMode().lessThan(lockMode);
      if (isVersionCheckNeeded)
      {
        checkVersion(i, persister, key.getIdentifier(), object, rs, session);
        
        session.getPersistenceContext().getEntry(object)
          .setLockMode(lockMode);
      }
    }
  }
  
  private Object instanceNotYetLoaded(ResultSet rs, int i, Loadable persister, String rowIdAlias, EntityKey key, LockMode lockMode, EntityKey optionalObjectKey, Object optionalObject, List hydratedObjects, SessionImplementor session)
    throws HibernateException, SQLException
  {
    String instanceClass = getInstanceClass(
      rs, 
      i, 
      persister, 
      key.getIdentifier(), 
      session);
    Object object;
    Object object;
    if ((optionalObjectKey != null) && (key.equals(optionalObjectKey))) {
      object = optionalObject;
    } else {
      object = session.instantiate(instanceClass, key.getIdentifier());
    }
    LockMode acquiredLockMode = lockMode == LockMode.NONE ? LockMode.READ : lockMode;
    loadFromResultSet(
      rs, 
      i, 
      object, 
      instanceClass, 
      key, 
      rowIdAlias, 
      acquiredLockMode, 
      persister, 
      session);
    


    hydratedObjects.add(object);
    
    return object;
  }
  
  private boolean isEagerPropertyFetchEnabled(int i)
  {
    boolean[] array = getEntityEagerPropertyFetches();
    return (array != null) && (array[i] != 0);
  }
  
  private void loadFromResultSet(ResultSet rs, int i, Object object, String instanceEntityName, EntityKey key, String rowIdAlias, LockMode lockMode, Loadable rootPersister, SessionImplementor session)
    throws SQLException, HibernateException
  {
    Serializable id = key.getIdentifier();
    

    Loadable persister = (Loadable)getFactory().getEntityPersister(instanceEntityName);
    if (log.isTraceEnabled()) {
      log.trace(
        "Initializing object from ResultSet: " + 
        MessageHelper.infoString(persister, id, getFactory()));
    }
    boolean eagerPropertyFetch = isEagerPropertyFetchEnabled(i);
    



    TwoPhaseLoad.addUninitializedEntity(
      key, 
      object, 
      persister, 
      lockMode, 
      !eagerPropertyFetch, 
      session);
    


    String[][] cols = persister == rootPersister ? 
      getEntityAliases()[i].getSuffixedPropertyAliases() : 
      getEntityAliases()[i].getSuffixedPropertyAliases(persister);
    
    Object[] values = persister.hydrate(
      rs, 
      id, 
      object, 
      rootPersister, 
      cols, 
      eagerPropertyFetch, 
      session);
    

    Object rowId = persister.hasRowId() ? rs.getObject(rowIdAlias) : null;
    
    AssociationType[] ownerAssociationTypes = getOwnerAssociationTypes();
    if ((ownerAssociationTypes != null) && (ownerAssociationTypes[i] != null))
    {
      String ukName = ownerAssociationTypes[i].getRHSUniqueKeyPropertyName();
      if (ukName != null)
      {
        int index = ((UniqueKeyLoadable)persister).getPropertyIndex(ukName);
        Type type = persister.getPropertyTypes()[index];
        





        EntityUniqueKey euk = new EntityUniqueKey(
          rootPersister.getEntityName(), 
          ukName, 
          type.semiResolve(values[index], session, object), 
          type, 
          session.getEntityMode(), session.getFactory());
        
        session.getPersistenceContext().addEntity(euk, object);
      }
    }
    TwoPhaseLoad.postHydrate(
      persister, 
      id, 
      values, 
      rowId, 
      object, 
      lockMode, 
      !eagerPropertyFetch, 
      session);
  }
  
  private String getInstanceClass(ResultSet rs, int i, Loadable persister, Serializable id, SessionImplementor session)
    throws HibernateException, SQLException
  {
    if (persister.hasSubclasses())
    {
      Object discriminatorValue = persister.getDiscriminatorType().nullSafeGet(
        rs, 
        getEntityAliases()[i].getSuffixedDiscriminatorAlias(), 
        session, 
        null);
      

      String result = persister.getSubclassForDiscriminatorValue(discriminatorValue);
      if (result == null) {
        throw new WrongClassException(
          "Discriminator: " + discriminatorValue, 
          id, 
          persister.getEntityName());
      }
      return result;
    }
    return persister.getEntityName();
  }
  
  private void advance(ResultSet rs, RowSelection selection)
    throws SQLException
  {
    int firstRow = getFirstRow(selection);
    if (firstRow != 0) {
      if (getFactory().getSettings().isScrollableResultSetsEnabled()) {
        rs.absolute(firstRow);
      } else {
        for (int m = 0; m < firstRow; m++) {
          rs.next();
        }
      }
    }
  }
  
  private static boolean hasMaxRows(RowSelection selection)
  {
    return (selection != null) && (selection.getMaxRows() != null);
  }
  
  private static int getFirstRow(RowSelection selection)
  {
    if ((selection == null) || (selection.getFirstRow() == null)) {
      return 0;
    }
    return selection.getFirstRow().intValue();
  }
  
  private int interpretFirstRow(int zeroBasedFirstResult)
  {
    return getFactory().getDialect().convertToFirstRowValue(zeroBasedFirstResult);
  }
  
  private static boolean useLimit(RowSelection selection, Dialect dialect)
  {
    return (dialect.supportsLimit()) && (hasMaxRows(selection));
  }
  
  protected final PreparedStatement prepareQueryStatement(QueryParameters queryParameters, boolean scroll, SessionImplementor session)
    throws SQLException, HibernateException
  {
    queryParameters.processFilters(getSQLString(), session);
    String sql = queryParameters.getFilteredSQL();
    Dialect dialect = getFactory().getDialect();
    RowSelection selection = queryParameters.getRowSelection();
    boolean useLimit = useLimit(selection, dialect);
    boolean hasFirstRow = getFirstRow(selection) > 0;
    boolean useOffset = (hasFirstRow) && (useLimit) && (dialect.supportsLimitOffset());
    boolean callable = queryParameters.isCallable();
    
    boolean useScrollableResultSetToSkip = (hasFirstRow) && 
      (!useOffset) && 
      (getFactory().getSettings().isScrollableResultSetsEnabled());
    ScrollMode scrollMode = scroll ? queryParameters.getScrollMode() : ScrollMode.SCROLL_INSENSITIVE;
    if (useLimit) {
      sql = dialect.getLimitString(
        sql.trim(), 
        useOffset ? getFirstRow(selection) : 0, 
        getMaxOrLimit(selection, dialect));
    }
    sql = preprocessSQL(sql, queryParameters, dialect);
    
    PreparedStatement st = null;
    if (callable) {
      st = 
        session.getBatcher().prepareCallableQueryStatement(sql, (scroll) || (useScrollableResultSetToSkip), scrollMode);
    } else {
      st = 
        session.getBatcher().prepareQueryStatement(sql, (scroll) || (useScrollableResultSetToSkip), scrollMode);
    }
    try
    {
      int col = 1;
      if ((useLimit) && (dialect.bindLimitParametersFirst())) {
        col += bindLimitParameters(st, col, selection);
      }
      if (callable) {
        col = dialect.registerResultSetOutParameter((CallableStatement)st, col);
      }
      col += bindParameterValues(st, queryParameters, col, session);
      if ((useLimit) && (!dialect.bindLimitParametersFirst())) {
        col += bindLimitParameters(st, col, selection);
      }
      if (!useLimit) {
        setMaxRows(st, selection);
      }
      if (selection != null)
      {
        if (selection.getTimeout() != null) {
          st.setQueryTimeout(selection.getTimeout().intValue());
        }
        if (selection.getFetchSize() != null) {
          st.setFetchSize(selection.getFetchSize().intValue());
        }
      }
      LockOptions lockOptions = queryParameters.getLockOptions();
      if ((lockOptions != null) && 
        (lockOptions.getTimeOut() != -1)) {
        if (!dialect.supportsLockTimeouts()) {
          log.debug(
            "Lock timeout [" + lockOptions.getTimeOut() + 
            "] requested but dialect reported to not support lock timeouts");
        } else if (dialect.isLockTimeoutParameterized()) {
          st.setInt(col++, lockOptions.getTimeOut());
        }
      }
      log.trace("Bound [" + col + "] parameters total");
    }
    catch (SQLException sqle)
    {
      session.getBatcher().closeQueryStatement(st, null);
      throw sqle;
    }
    catch (HibernateException he)
    {
      session.getBatcher().closeQueryStatement(st, null);
      throw he;
    }
    return st;
  }
  
  private static int getMaxOrLimit(RowSelection selection, Dialect dialect)
  {
    int firstRow = dialect.convertToFirstRowValue(getFirstRow(selection));
    int lastRow = selection.getMaxRows().intValue();
    if (dialect.useMaxForLimit()) {
      return lastRow + firstRow;
    }
    return lastRow;
  }
  
  private int bindLimitParameters(PreparedStatement statement, int index, RowSelection selection)
    throws SQLException
  {
    Dialect dialect = getFactory().getDialect();
    if (!dialect.supportsVariableLimit()) {
      return 0;
    }
    if (!hasMaxRows(selection)) {
      throw new AssertionFailure("no max results set");
    }
    int firstRow = interpretFirstRow(getFirstRow(selection));
    int lastRow = getMaxOrLimit(selection, dialect);
    boolean hasFirstRow = (dialect.supportsLimitOffset()) && ((firstRow > 0) || (dialect.forceLimitUsage()));
    boolean reverse = dialect.bindLimitParametersInReverseOrder();
    if (hasFirstRow) {
      statement.setInt(index + (reverse ? 1 : 0), firstRow);
    }
    statement.setInt(index + ((reverse) || (!hasFirstRow) ? 0 : 1), lastRow);
    return hasFirstRow ? 2 : 1;
  }
  
  private void setMaxRows(PreparedStatement st, RowSelection selection)
    throws SQLException
  {
    if (hasMaxRows(selection)) {
      st.setMaxRows(selection.getMaxRows().intValue() + interpretFirstRow(getFirstRow(selection)));
    }
  }
  
  protected int bindParameterValues(PreparedStatement statement, QueryParameters queryParameters, int startIndex, SessionImplementor session)
    throws SQLException
  {
    int span = 0;
    span += bindPositionalParameters(statement, queryParameters, startIndex, session);
    span += bindNamedParameters(statement, queryParameters.getNamedParameters(), startIndex + span, session);
    return span;
  }
  
  protected int bindPositionalParameters(PreparedStatement statement, QueryParameters queryParameters, int startIndex, SessionImplementor session)
    throws SQLException, HibernateException
  {
    Object[] values = queryParameters.getFilteredPositionalParameterValues();
    Type[] types = queryParameters.getFilteredPositionalParameterTypes();
    int span = 0;
    for (int i = 0; i < values.length; i++)
    {
      types[i].nullSafeSet(statement, values[i], startIndex + span, session);
      span += types[i].getColumnSpan(getFactory());
    }
    return span;
  }
  
  protected int bindNamedParameters(PreparedStatement statement, Map namedParams, int startIndex, SessionImplementor session)
    throws SQLException, HibernateException
  {
    if (namedParams != null)
    {
      Iterator iter = namedParams.entrySet().iterator();
      int result = 0;
      while (iter.hasNext())
      {
        Map.Entry e = (Map.Entry)iter.next();
        String name = (String)e.getKey();
        TypedValue typedval = (TypedValue)e.getValue();
        int[] locs = getNamedParameterLocs(name);
        for (int i = 0; i < locs.length; i++)
        {
          if (log.isDebugEnabled()) {
            log.debug(
              "bindNamedParameters() " + 
              typedval.getValue() + " -> " + name + 
              " [" + (locs[i] + startIndex) + "]");
          }
          typedval.getType().nullSafeSet(statement, typedval.getValue(), locs[i] + startIndex, session);
        }
        result += locs.length;
      }
      return result;
    }
    return 0;
  }
  
  public int[] getNamedParameterLocs(String name)
  {
    throw new AssertionFailure("no named parameters");
  }
  
  protected final ResultSet getResultSet(PreparedStatement st, boolean autodiscovertypes, boolean callable, RowSelection selection, SessionImplementor session)
    throws SQLException, HibernateException
  {
    ResultSet rs = null;
    try
    {
      Dialect dialect = getFactory().getDialect();
      if (callable) {
        rs = session.getBatcher().getResultSet((CallableStatement)st, dialect);
      } else {
        rs = session.getBatcher().getResultSet(st);
      }
      rs = wrapResultSetIfEnabled(rs, session);
      if ((!dialect.supportsLimitOffset()) || (!useLimit(selection, dialect))) {
        advance(rs, selection);
      }
      if (autodiscovertypes) {
        autoDiscoverTypes(rs);
      }
      return rs;
    }
    catch (SQLException sqle)
    {
      session.getBatcher().closeQueryStatement(st, rs);
      throw sqle;
    }
  }
  
  protected void autoDiscoverTypes(ResultSet rs)
  {
    throw new AssertionFailure("Auto discover types not supported in this loader");
  }
  
  private synchronized ResultSet wrapResultSetIfEnabled(ResultSet rs, SessionImplementor session)
  {
    if (session.getFactory().getSettings().isWrapResultSetsEnabled()) {
      try
      {
        log.debug("Wrapping result set [" + rs + "]");
        return session.getFactory()
          .getSettings()
          .getJdbcSupport().wrap(rs, retreiveColumnNameToIndexCache(rs));
      }
      catch (SQLException e)
      {
        log.info("Error wrapping result set", e);
        return rs;
      }
    }
    return rs;
  }
  
  private ColumnNameCache retreiveColumnNameToIndexCache(ResultSet rs)
    throws SQLException
  {
    if (this.columnNameCache == null)
    {
      log.trace("Building columnName->columnIndex cache");
      this.columnNameCache = new ColumnNameCache(rs.getMetaData().getColumnCount());
    }
    return this.columnNameCache;
  }
  
  protected final List loadEntity(SessionImplementor session, Object id, Type identifierType, Object optionalObject, String optionalEntityName, Serializable optionalIdentifier, EntityPersister persister, LockOptions lockOptions)
    throws HibernateException
  {
    if (log.isDebugEnabled()) {
      log.debug(
        "loading entity: " + 
        MessageHelper.infoString(persister, id, identifierType, getFactory()));
    }
    try
    {
      QueryParameters qp = new QueryParameters();
      qp.setPositionalParameterTypes(new Type[] { identifierType });
      qp.setPositionalParameterValues(new Object[] { id });
      qp.setOptionalObject(optionalObject);
      qp.setOptionalEntityName(optionalEntityName);
      qp.setOptionalId(optionalIdentifier);
      qp.setLockOptions(lockOptions);
      result = doQueryAndInitializeNonLazyCollections(session, qp, false);
    }
    catch (SQLException sqle)
    {
      List result;
      Loadable[] persisters = getEntityPersisters();
      throw JDBCExceptionHelper.convert(
        this.factory.getSQLExceptionConverter(), 
        sqle, 
        "could not load an entity: " + 
        MessageHelper.infoString(persisters[(persisters.length - 1)], id, identifierType, getFactory()), 
        getSQLString());
    }
    List result;
    log.debug("done entity load");
    
    return result;
  }
  
  protected final List loadEntity(SessionImplementor session, Object key, Object index, Type keyType, Type indexType, EntityPersister persister)
    throws HibernateException
  {
    if (log.isDebugEnabled()) {
      log.debug("loading collection element by index");
    }
    try
    {
      result = doQueryAndInitializeNonLazyCollections(
        session, 
        new QueryParameters(
        new Type[] { keyType, indexType }, 
        new Object[] { key, index }), 
        
        false);
    }
    catch (SQLException sqle)
    {
      List result;
      throw JDBCExceptionHelper.convert(
        this.factory.getSQLExceptionConverter(), 
        sqle, 
        "could not collection element by index", 
        getSQLString());
    }
    List result;
    log.debug("done entity load");
    
    return result;
  }
  
  public final List loadEntityBatch(SessionImplementor session, Serializable[] ids, Type idType, Object optionalObject, String optionalEntityName, Serializable optionalId, EntityPersister persister, LockOptions lockOptions)
    throws HibernateException
  {
    if (log.isDebugEnabled()) {
      log.debug(
        "batch loading entity: " + 
        MessageHelper.infoString(persister, ids, getFactory()));
    }
    Type[] types = new Type[ids.length];
    Arrays.fill(types, idType);
    try
    {
      QueryParameters qp = new QueryParameters();
      qp.setPositionalParameterTypes(types);
      qp.setPositionalParameterValues(ids);
      qp.setOptionalObject(optionalObject);
      qp.setOptionalEntityName(optionalEntityName);
      qp.setOptionalId(optionalId);
      qp.setLockOptions(lockOptions);
      result = doQueryAndInitializeNonLazyCollections(session, qp, false);
    }
    catch (SQLException sqle)
    {
      List result;
      throw JDBCExceptionHelper.convert(
        this.factory.getSQLExceptionConverter(), 
        sqle, 
        "could not load an entity batch: " + 
        MessageHelper.infoString(getEntityPersisters()[0], ids, getFactory()), 
        getSQLString());
    }
    List result;
    log.debug("done entity batch load");
    
    return result;
  }
  
  public final void loadCollection(SessionImplementor session, Serializable id, Type type)
    throws HibernateException
  {
    if (log.isDebugEnabled()) {
      log.debug(
        "loading collection: " + 
        MessageHelper.collectionInfoString(getCollectionPersisters()[0], id, getFactory()));
    }
    Serializable[] ids = { id };
    try
    {
      doQueryAndInitializeNonLazyCollections(
        session, 
        new QueryParameters(new Type[] { type }, ids, ids), 
        true);
    }
    catch (SQLException sqle)
    {
      throw JDBCExceptionHelper.convert(
        this.factory.getSQLExceptionConverter(), 
        sqle, 
        "could not initialize a collection: " + 
        MessageHelper.collectionInfoString(getCollectionPersisters()[0], id, getFactory()), 
        getSQLString());
    }
    log.debug("done loading collection");
  }
  
  public final void loadCollectionBatch(SessionImplementor session, Serializable[] ids, Type type)
    throws HibernateException
  {
    if (log.isDebugEnabled()) {
      log.debug(
        "batch loading collection: " + 
        MessageHelper.collectionInfoString(getCollectionPersisters()[0], ids, getFactory()));
    }
    Type[] idTypes = new Type[ids.length];
    Arrays.fill(idTypes, type);
    try
    {
      doQueryAndInitializeNonLazyCollections(
        session, 
        new QueryParameters(idTypes, ids, ids), 
        true);
    }
    catch (SQLException sqle)
    {
      throw JDBCExceptionHelper.convert(
        this.factory.getSQLExceptionConverter(), 
        sqle, 
        "could not initialize a collection batch: " + 
        MessageHelper.collectionInfoString(getCollectionPersisters()[0], ids, getFactory()), 
        getSQLString());
    }
    log.debug("done batch load");
  }
  
  protected final void loadCollectionSubselect(SessionImplementor session, Serializable[] ids, Object[] parameterValues, Type[] parameterTypes, Map namedParameters, Type type)
    throws HibernateException
  {
    Type[] idTypes = new Type[ids.length];
    Arrays.fill(idTypes, type);
    try
    {
      doQueryAndInitializeNonLazyCollections(session, 
        new QueryParameters(parameterTypes, parameterValues, namedParameters, ids), 
        true);
    }
    catch (SQLException sqle)
    {
      throw JDBCExceptionHelper.convert(
        this.factory.getSQLExceptionConverter(), 
        sqle, 
        "could not load collection by subselect: " + 
        MessageHelper.collectionInfoString(getCollectionPersisters()[0], ids, getFactory()), 
        getSQLString());
    }
  }
  
  protected List list(SessionImplementor session, QueryParameters queryParameters, Set querySpaces, Type[] resultTypes)
    throws HibernateException
  {
    boolean cacheable = (this.factory.getSettings().isQueryCacheEnabled()) && 
      (queryParameters.isCacheable());
    if (cacheable) {
      return listUsingQueryCache(session, queryParameters, querySpaces, resultTypes);
    }
    return listIgnoreQueryCache(session, queryParameters);
  }
  
  private List listIgnoreQueryCache(SessionImplementor session, QueryParameters queryParameters)
  {
    return getResultList(doList(session, queryParameters), queryParameters.getResultTransformer());
  }
  
  private List listUsingQueryCache(SessionImplementor session, QueryParameters queryParameters, Set querySpaces, Type[] resultTypes)
  {
    QueryCache queryCache = this.factory.getQueryCache(queryParameters.getCacheRegion());
    
    Set filterKeys = FilterKey.createFilterKeys(
      session.getLoadQueryInfluencers().getEnabledFilters(), 
      session.getEntityMode());
    
    QueryKey key = QueryKey.generateQueryKey(
      getSQLString(), 
      queryParameters, 
      filterKeys, 
      session);
    

    List result = getResultFromQueryCache(
      session, 
      queryParameters, 
      querySpaces, 
      resultTypes, 
      queryCache, 
      key);
    if (result == null)
    {
      result = doList(session, queryParameters);
      
      putResultInQueryCache(
        session, 
        queryParameters, 
        resultTypes, 
        queryCache, 
        key, 
        result);
    }
    return getResultList(result, queryParameters.getResultTransformer());
  }
  
  private List getResultFromQueryCache(SessionImplementor session, QueryParameters queryParameters, Set querySpaces, Type[] resultTypes, QueryCache queryCache, QueryKey key)
  {
    List result = null;
    if (session.getCacheMode().isGetEnabled())
    {
      boolean isImmutableNaturalKeyLookup = (queryParameters.isNaturalKeyLookup()) && 
        (getEntityPersisters()[0].getEntityMetamodel().hasImmutableNaturalId());
      
      PersistenceContext persistenceContext = session.getPersistenceContext();
      boolean defaultReadOnlyOrig = persistenceContext.isDefaultReadOnly();
      if (queryParameters.isReadOnlyInitialized()) {
        persistenceContext.setDefaultReadOnly(queryParameters.isReadOnly());
      } else {
        queryParameters.setReadOnly(persistenceContext.isDefaultReadOnly());
      }
      try
      {
        result = queryCache.get(key, resultTypes, isImmutableNaturalKeyLookup, querySpaces, session);
      }
      finally
      {
        persistenceContext.setDefaultReadOnly(defaultReadOnlyOrig);
      }
      if (this.factory.getStatistics().isStatisticsEnabled()) {
        if (result == null) {
          this.factory.getStatisticsImplementor().queryCacheMiss(getQueryIdentifier(), queryCache.getRegion().getName());
        } else {
          this.factory.getStatisticsImplementor().queryCacheHit(getQueryIdentifier(), queryCache.getRegion().getName());
        }
      }
    }
    return result;
  }
  
  private void putResultInQueryCache(SessionImplementor session, QueryParameters queryParameters, Type[] resultTypes, QueryCache queryCache, QueryKey key, List result)
  {
    if (session.getCacheMode().isPutEnabled())
    {
      boolean put = queryCache.put(key, resultTypes, result, queryParameters.isNaturalKeyLookup(), session);
      if ((put) && (this.factory.getStatistics().isStatisticsEnabled())) {
        this.factory.getStatisticsImplementor().queryCachePut(getQueryIdentifier(), queryCache.getRegion().getName());
      }
    }
  }
  
  protected List doList(SessionImplementor session, QueryParameters queryParameters)
    throws HibernateException
  {
    boolean stats = getFactory().getStatistics().isStatisticsEnabled();
    long startTime = 0L;
    if (stats) {
      startTime = System.currentTimeMillis();
    }
    try
    {
      result = doQueryAndInitializeNonLazyCollections(session, queryParameters, true);
    }
    catch (SQLException sqle)
    {
      List result;
      throw JDBCExceptionHelper.convert(
        this.factory.getSQLExceptionConverter(), 
        sqle, 
        "could not execute query", 
        getSQLString());
    }
    List result;
    if (stats) {
      getFactory().getStatisticsImplementor().queryExecuted(
        getQueryIdentifier(), 
        result.size(), 
        System.currentTimeMillis() - startTime);
    }
    return result;
  }
  
  protected void checkScrollability()
    throws HibernateException
  {}
  
  protected boolean needsFetchingScroll()
  {
    return false;
  }
  
  protected ScrollableResults scroll(QueryParameters queryParameters, Type[] returnTypes, HolderInstantiator holderInstantiator, SessionImplementor session)
    throws HibernateException
  {
    checkScrollability();
    
    boolean stats = (getQueryIdentifier() != null) && 
      (getFactory().getStatistics().isStatisticsEnabled());
    long startTime = 0L;
    if (stats) {
      startTime = System.currentTimeMillis();
    }
    try
    {
      PreparedStatement st = prepareQueryStatement(queryParameters, true, session);
      ResultSet rs = getResultSet(st, queryParameters.hasAutoDiscoverScalarTypes(), queryParameters.isCallable(), queryParameters.getRowSelection(), session);
      if (stats) {
        getFactory().getStatisticsImplementor().queryExecuted(
          getQueryIdentifier(), 
          0, 
          System.currentTimeMillis() - startTime);
      }
      if (needsFetchingScroll()) {
        return new FetchingScrollableResultsImpl(
          rs, 
          st, 
          session, 
          this, 
          queryParameters, 
          returnTypes, 
          holderInstantiator);
      }
      return new ScrollableResultsImpl(
        rs, 
        st, 
        session, 
        this, 
        queryParameters, 
        returnTypes, 
        holderInstantiator);
    }
    catch (SQLException sqle)
    {
      throw JDBCExceptionHelper.convert(
        this.factory.getSQLExceptionConverter(), 
        sqle, 
        "could not execute query using scroll", 
        getSQLString());
    }
  }
  
  protected void postInstantiate() {}
  
  protected abstract EntityAliases[] getEntityAliases();
  
  protected abstract CollectionAliases[] getCollectionAliases();
  
  protected String getQueryIdentifier()
  {
    return null;
  }
  
  public final SessionFactoryImplementor getFactory()
  {
    return this.factory;
  }
  
  public String toString()
  {
    return getClass().getName() + '(' + getSQLString() + ')';
  }
}
