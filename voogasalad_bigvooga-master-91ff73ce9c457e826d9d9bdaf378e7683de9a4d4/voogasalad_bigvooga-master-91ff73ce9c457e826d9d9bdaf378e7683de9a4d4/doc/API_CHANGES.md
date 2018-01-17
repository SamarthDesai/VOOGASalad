## External API Engine: 

### Additional Classes:

class OperationFactory

   Operation makeOperation(String operationName, Object... parameters)

   List(String) getParameters(String operationName)

   List(String) getOperations(String operationType)
   
class ActionFactory

   List(String) getCategories()
   
   List(String) getActions(String category)

   Action makeAction(String actionName, Object... parameters)
   
   List(String) getParameters(String actionName)
   
class GameLayer

   Replaces GameWorld, which now just holds layers

### Changes to Existing

class GameWorld

   Now holds Layers

   void addLayer(GameLayer layer)
   
   void addGlobalVars(GlobalVariables gvs)
   