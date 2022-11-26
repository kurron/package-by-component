## Primary Components
GURPS Online is implemented as independent services, interacting with each other via a message broker. 

![](embed:container-gurps)

### Web User Interface
The user interface ...
![](embed:gui)

### API Gateway
The API gateway is the "front door" into the system for the web clients.  Its job is to translate the synchronous HTTP requests into the asynchronous messages that the various services react to.
![](embed:api-gateway)

### Message Broker
The message broker is the primary communication mechanism used in the system and is responsible for routing messages to their intended consumers. GURPS Online uses 3 types of messages:
* command - a request to modify something in the system, e.g. "change character 123's strength to 21"
* event - signals a past occurrence, a fact, e.g. "character 123's strength is now 21"
* query - a request for information, "give me character 123's full details"

Command and Query messages are intended for a specific service while Events have no intended recipient, consumable by any who are interested. 
![](embed:message-broker)

### User Service
The user service manages all things related to GURPS Online users, including creation, modification and deletion. A user must exist prior to interacting with the system and is currently managed by the administrator. The service also handles authentication requests made by the UI.
![](embed:user-service)

### Campaign Service
The campaign service manages all things related to campaigns, including creation, modification and deletion.
![](embed:campaign-service)

### Character Service
The character service manages all things related to campaigns, including creation, modification and deletion.
![](embed:character-service)

### Asset Service
The asset service manages all things related to campaigns, including creation, modification and deletion. Unlike other services, the asset service supports bulk operations, including uploading of data files containing assets to be imported into the system. Types of assets in the system include:
* generic non-player characters, e.g. librarian
* character advantages, e.g. Danger Sense
* character disadvantages, e.g. Bad Sight
* skills, e.g. Brawling
* armor, e.g. Ballistic Vest
* shields, e.g. Medium Shield
* weapons, e.g. Sniper Rifle
* physical feats, e.g. climbing up a tree
* 
![](embed:asset-service)
