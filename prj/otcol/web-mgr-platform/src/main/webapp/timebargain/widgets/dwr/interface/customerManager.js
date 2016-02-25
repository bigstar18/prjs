
// Provide a default path to dwr.engine
if (dwr == null) var dwr = {};
if (dwr.engine == null) dwr.engine = {};
if (DWREngine == null) var DWREngine = dwr.engine;

dwr.engine._defaultPath = '../dwr';

if (customerManager == null) var customerManager = {};
customerManager._path = '../dwr';
customerManager.getCustomerName = function(p0, callback) {
  dwr.engine._execute(customerManager._path, 'customerManager', 'getCustomerName', p0, callback);
}
customerManager.getMaxCustomerID = function(p0, callback) {
  dwr.engine._execute(customerManager._path, 'customerManager', 'getMaxCustomerID', p0, callback);
}
customerManager.getMaxCustomerID = function(p0, p1, callback) {
  dwr.engine._execute(customerManager._path, 'customerManager', 'getMaxCustomerID', p0, p1, callback);
}
customerManager.getMinCustomerID = function(p0, callback) {
  dwr.engine._execute(customerManager._path, 'customerManager', 'getMinCustomerID', p0, callback);
}
customerManager.getMinCustomerID = function(p0, p1, callback) {
  dwr.engine._execute(customerManager._path, 'customerManager', 'getMinCustomerID', p0, p1, callback);
}
customerManager.getCustomerById = function(p0, callback) {
  dwr.engine._execute(customerManager._path, 'customerManager', 'getCustomerById', p0, callback);
}
