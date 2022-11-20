# https://github.com/structurizr/dsl/blob/master/docs/language-reference.md

#(Person|SoftwareSystem|Container|Component|DeploymentNode|InfrastructureNode|SoftwareSystemInstance|ContainerInstance|Custom)

!constant CODE_NAME "Project Alpha"
!constant GROUP_NAME "Group"

/*
multi line
*/

# single line
// single line

workspace "GURPS Online" "Second" {
    !identifiers flat
    !impliedRelationships true
   #!include <file|directory|url>

    !docs documents
    !adrs decisions

    !constant FOO "Some text you want to reuse."

    model {
        gary = Person "Gary" {
            description "Game Master"
        }
        penny = Person "Penny" {
            description "Player"
        }
        adam = Person "Adam" {
            description "System Administrator"
        }
        gurps = softwareSystem "GURPS Online" {
            description "Online version of GURPS release 4"
            perspectives {
            }
            apiGateway = container "API Gateway" {
                description "GURPS REST APIs"
                technology "Spring GraphQL?"
                tags "tag"
                perspectives {
                }
                component "Foo Component" {
                    description "Some description"
                    technology "Some technology"
                    perspectives {
                    }
                }
            }
            userStore = container "User Store" {
                description "Some description"
                technology "MongoDB"
                tags "DataStore"
                perspectives {
                }
                userInProgressCollection = component "In-progress Collection" {
                    description "User information still being modified"
                    technology "MongoDB"
                    perspectives {
                    }
                }
                userCollection = component "User Collection" {
                    description "Read-only User information"
                    technology "MongoDB"
                    perspectives {
                    }
                }
            }
            container "Asset Store" {
                description "Some description"
                technology "MongoDB"
                tags "DataStore"
                perspectives {
                }
                assetInProgressCollection = component "In-progress Collection" {
                    description "Asset information still being modified"
                    technology "MongoDB"
                    perspectives {
                    }
                }
                assetCollection = component "Asset Collection" {
                    description "Read-only Asset information"
                    technology "MongoDB"
                    perspectives {
                    }
                }
            }
            container "Campaign Store" {
                description "Some description"
                technology "MongoDB"
                tags "DataStore"
                perspectives {
                }
                campaignInProgressCollection = component "In-progress Campaign" {
                    description "Campaign information still being modified"
                    technology "MongoDB"
                    perspectives {
                    }
                }
                campaignCollection = component "Campaign Collection" {
                    description "Read-only Campaign information"
                    technology "MongoDB"
                    perspectives {
                    }
                }
            }
            container "Character Store" {
                description "Some description"
                technology "MongoDB"
                tags "DataStore"
                perspectives {
                }
                characterInProgressCollection = component "In-progress Characters" {
                    description "Character information still being modified"
                    technology "MongoDB"
                    perspectives {
                    }
                }
                characterCollection = component "Character Collection" {
                    description "Read-only Character information"
                    technology "MongoDB"
                    perspectives {
                    }
                }
            }
            gui = container "Web User Interface" {
                description "Graphical user interface"
                technology "HTML, Javascript"
                tags "WebUI"
                perspectives {
                }
                administratorUI = component "Administrator UI" {
                    description "Administrator UI"
                    technology "HTML, Javascript"
                    perspectives {
                    }
                }
                campaignUI = component "Campaign UI" {
                    description "Campaign UI"
                    technology "HTML, Javascript"
                    perspectives {
                    }
                }
                characterUI = component "Character UI" {
                    description "Character UI"
                    technology "HTML, Javascript"
                    perspectives {
                    }
                }
                gary -> campaignUI "manages campaigns" "JSON over HTTP" "json-over-http" {
                }
                penny -> characterUI "manages characters" "JSON over HTTP" "json-over-http" {
                }
                adam -> administratorUI "manages Users" "JSON over HTTP" "json-over-http" {
                }
                campaignUI -> apiGateway "sends request" "JSON over HTTP" "json-over-http" {
                }
                characterUI -> apiGateway "sends request" "JSON over HTTP" "json-over-http" {
                }
                administratorUI -> apiGateway "sends request" "JSON over HTTP" "json-over-http" {
                }
            }
            userService = container "User Service" {
                description "User management functions"
                technology "Kotlin, Spring Boot"
                tags "Microservice"
                perspectives {
                }
                userCommandProcessor = component "Command Processor" {
                    description "Handles command messages"
                    technology "Kotlin"
                    perspectives {
                    }
                    this -> userInProgressCollection "saves new version to" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
                userEventProcessor = component "Event Processor" {
                    description "Handles event messages"
                    technology "Kotlin"
                    perspectives {
                    }
                    this -> userCollection "saves user changes to" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
                component "Query Processor" {
                    description "Handles query messages"
                    technology "Kotlin"
                    perspectives {
                    }
                    this -> userCollection "loads users from" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
            }
            campaignService = container "Campaign Service" {
                description "Campaign management functions"
                technology "Kotlin, Spring Boot"
                tags "Microservice"
                perspectives {
                }
                campaignCommandProcessor = component "Command Processor" {
                    description "Handles command messages"
                    technology "Kotlin"
                    perspectives {
                    }
                    this -> campaignInProgressCollection "saves new version to" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
                campaignEventProcessor = component "Event Processor" {
                    description "Handles event messages"
                    technology "Kotlin"
                    perspectives {
                    }
                    this -> campaignCollection "saves campaign changes to" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
                campaignQueryProcessor = component "Query Processor" {
                    description "Handles query messages"
                    technology "Kotlin"
                    perspectives {
                    }
                    this -> campaignCollection "loads campaigns from" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
            }
            characterService = container "Character Service" {
                description "Character management functions"
                technology "Kotlin, Spring Boot"
                tags "Microservice"
                perspectives {
                }
                characterCommandProcessor = component "Command Processor" {
                    description "Handles command messages"
                    technology "Kotlin"
                    perspectives {
                    }
                    this -> characterInProgressCollection "saves new version to" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
                characterEventProcessor = component "Event Processor" {
                    description "Handles event messages"
                    technology "Kotlin"
                    perspectives {
                    }
                    this -> characterCollection "saves campaign changes to" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
                characterQueryProcessor = component "Query Processor" {
                    description "Handles query messages"
                    technology "Kotlin"
                    perspectives {
                    }
                    this -> characterCollection "loads characters from" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
            }
            assetService = container "Assets Service" {
                description "Asset management functions"
                technology "Kotlin, Spring Boot"
                tags "Microservice"
                perspectives {
                }
                component "Command Processor" {
                    description "Handles command messages"
                    technology "Kotlin"
                    perspectives {
                    }
                    this -> assetInProgressCollection "saves new version to" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
                component "Event Processor" {
                    description "Handles event messages"
                    technology "Kotlin"
                    perspectives {
                    }
                    this -> assetCollection "saves asset changes to" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
                component "Query Processor" {
                    description "Handles query messages"
                    technology "Kotlin"
                    perspectives {
                    }
                    this -> assetCollection "loads assets from" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
            }
            messageBroker = container "Message Broker" {
                description "Messaging fabric"
                technology "RabbitMQ"
                tags "MessageBroker"
                perspectives {
                }
                apiGateway -> this "sends messages to" "JSON over AMQP" "json-over-amqp"
                this -> userService "sends messages to" "JSON over AMQP" "json-over-amqp"
                this -> assetService "sends messages to" "JSON over AMQP" "json-over-amqp"
                this -> characterService "sends messages to" "JSON over AMQP" "json-over-amqp"
                this -> campaignService "sends messages to" "JSON over AMQP" "json-over-amqp"
                component "Point-to-Multipoint Messages" {
                    description "Fanout Exchange, routing messages to all queues"
                    technology "RabbitMQ"
                    perspectives {
                    }
                }
                component "Point-to-Point Messages" {
                    description "Topic Exchange, routing messages to the proper queue"
                    technology "RabbitMQ"
                    perspectives {
                    }
                }
            }
        }
        production = deploymentEnvironment "production" {
            deploymentNode "MongoDB Cluster" {
                description "MongoDB fault tolerant cluster"
                technology "Hosted MongoDB"
            }
            deploymentNode "RabbitMQ Cluster" {
                description "RabbitMQ fault tolerant cluster"
                technology "Hosted RabbitMQ"
                //containerInstance pointToMultipoint
            }
            productionKubernetes = deploymentNode "Kubernetes Cluster" {
                description "On-prem Kubernetes cluster"
                technology "K3S, Rancher"
                deploymentNode "Command Processor Pods" {
                    description "On-prem Kubernetes cluster"
                    technology "Kubernetes"
                    instances 8
                }
                deploymentNode "Event Processor Pods" {
                    description "On-prem Kubernetes cluster"
                    technology "Kubernetes"
                    instances 8
                }
                deploymentNode "Query Processor Pods" {
                    description "On-prem Kubernetes cluster"
                    technology "Kubernetes"
                    instances 16
                }
            }
        }
    }

    # https://visme.co/blog/website-color-schemes/
    views {
        theme default
        styles {
            element "MessageBroker" {
                shape Cylinder
                background #E8A87C
            }
            element "DataStore" {
                shape Cylinder
                background #E27D60
            }
            element "Cron" {
                shape Robot
            }
            element "FileStore" {
                shape Folder
                background #dac292
            }
            # shape <Box|RoundedBox|Circle|Ellipse|Hexagon|Cylinder|Pipe|Person|Robot|Folder|WebBrowser|MobileDevicePortrait|MobileDeviceLandscape|Component>
            element "Channel" {
                shape Pipe
                background #C38D9E
            }
            element "Channel Adapter" {
                shape Ellipse
                background #41B3A3
            }
            element "Microservice" {
                shape Hexagon
            }
            element "CommandLine" {
                shape Box
            }
            element "WebUI" {
                shape WebBrowser
            }

            relationship "json-over-http" {
                thickness 2
                style solid
                color #242582
            }
            relationship "json-over-amqp" {
                thickness 2
                style dashed
                color #553D67
            }
            relationship "json-over-mongodb-wire-protocol" {
                thickness 2
                style dashed
                color #F64C72
            }
        }

        systemContext "gurps" "system-context" "Double click on + to expand view" {
            title "High level view of the solution"
            include *
            autoLayout
        }

        container "gurps" "container-gurps" "Double click on + to expand view" {
            title "View of cooperating services"
            include *
            autoLayout
        }

        component "messageBroker" "message-broker" "Double click on + to expand view" {
            title "Message Broker"
            include *
            autoLayout
        }

        component "userService" "user-service" "Double click on + to expand view" {
            title "User Service Collaborators"
            include *
            autoLayout
        }

        component "campaignService" "campaign-service" "Double click on + to expand view" {
            title "Campaign Service Collaborators"
            include *
            autoLayout
        }

        component "characterService" "character-service" "Double click on + to expand view" {
            title "Character Service Collaborators"
            include *
            autoLayout
        }

        component "assetService" "asset-service" "Double click on + to expand view" {
            title "Asset Service Collaborators"
            include *
            autoLayout
        }

        component "userStore" "user-store" "Double click on + to expand view" {
            title "User Storage Collections"
            include *
            autoLayout
        }

        component "gui" "gui" "Double click on + to expand view" {
            title "Graphical User Interface components"
            include *
            autoLayout
        }

        component "apiGateway" "api-gateway" "Double click on + to expand view" {
            title "API Gateway components"
            include *
            autoLayout
        }

        deployment "*" "production" "deployment-production" "Production deployment overview" {
            title "Production Deployment Diagram"
            description "Some instances are hosted off-site"
            include *
            autoLayout
        }
/*
        dynamic "cli" "cli-alpha" "Click on button to animate" {
            title "User creation flow"
            autoLayout lr

#            <element identifier> -> <element identifier> [description] [technology]
#            <relationship identifier> [description]
             adam -> userCommandsCLI "create a new user"
             userCommandsCLI -> userCommands "sends create-new-user command"
             userCommands -> userCommandProcessor "forwards create-new-user command"
             userCommandProcessor -> userInProgressCollection "saves new user data"
             userCommandProcessor -> userEventProcessor "sends new-user-created event"
             userEventProcessor -> userCollection "save user to"
        }
*/
    }
}