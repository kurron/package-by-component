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
        cli = softwareSystem "Command Line Interface" {
            description "Bulk operation tool"
            tags "CommandLine"
            perspectives {
            }
            adam -> this "bulk uploads assets" "JSON file" "TAG" {
            }
        }
        gurps = softwareSystem "GURPS Online" {
            description "Online version of GURPS release 4"
            perspectives {
            }
            backend = container "Backend Services" {
                description "Services used by the UI, broken up by feature"
                technology "Spring Cloud Stream"
                tags "tag"
                perspectives {
                }
                userFeature = component "User Feature" {
                    description "Module providing user management features"
                    technology "Spring Cloud Stream"
                    perspectives {
                    }
                }
                campaignFeature = component "Campaign Feature" {
                    description "Module providing campaign management features"
                    technology "Spring Cloud Stream"
                    perspectives {
                    }
                }
                characterFeature = component "Character Feature" {
                    description "Module providing character management features"
                    technology "Spring Cloud Stream"
                    perspectives {
                    }
                }
                assetFeature = component "Asset Feature" {
                    description "Module providing asset management features"
                    technology "Spring Cloud Stream"
                    perspectives {
                    }
                }
            }
            apiGateway = container "API Gateway" {
                description "GURPS REST APIs"
                technology "Spring Cloud API Gateway"
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
            database = container "GURPS Database" {
                description "Persistent storage of GURPS data"
                technology "MongoDB"
                tags "DataStore"
                perspectives {
                }
                component "User Tenant" {
                    description "User information in its own space"
                    technology "MongoDB"
                    perspectives {
                    }
                    userFeature -> this "read/write user data" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
                component "Campaign Tenant" {
                    description "Campaign information in its own space"
                    technology "MongoDB"
                    perspectives {
                    }
                    campaignFeature -> this "read/write user data" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
                component "Character Tenant" {
                    description "Character information in its own space"
                    technology "MongoDB"
                    perspectives {
                    }
                    characterFeature -> this "read/write user data" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
                component "Asset Tenant" {
                    description "Asset information in its own space"
                    technology "MongoDB"
                    perspectives {
                    }
                    assetFeature -> this "read/write user data" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
            }
            frontend = container "Web User Interface" {
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

            messageBroker = container "Message Broker" {
                description "Messaging fabric"
                technology "RabbitMQ"
                tags "MessageBroker"
                perspectives {
                }
                apiGateway -> this "sends messages to" "JSON over AMQP" "json-over-amqp"
                this -> backend "sends messages to" "JSON over AMQP" "json-over-amqp"
                cli -> this "sends messages to" "JSON over AMQP" "json-over-amqp"
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
                containerInstance database
            }
            deploymentNode "RabbitMQ Cluster" {
                description "RabbitMQ fault tolerant cluster"
                technology "Hosted RabbitMQ"
                containerInstance messageBroker
            }
            productionKubernetes = deploymentNode "Kubernetes Cluster" {
                description "On-prem Kubernetes cluster"
                technology "K3S, Rancher"
                deploymentNode "Backend Pods" {
                    description "On-prem Kubernetes cluster"
                    technology "Kubernetes"
                    instances 8
                    containerInstance backend
                }
                deploymentNode "Frontend Pods" {
                    description "On-prem Kubernetes cluster"
                    technology "Kubernetes"
                    instances 8
                    containerInstance frontend
                }
                deploymentNode "API Gateway Pods" {
                    description "On-prem Kubernetes cluster"
                    technology "Kubernetes"
                    instances 16
                    containerInstance apiGateway
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

        component "backend" "backend" "Double click on + to expand view" {
            title "Loosely coupled monolith"
            include *
            autoLayout
        }

        component "messageBroker" "message-broker" "Double click on + to expand view" {
            title "Message Broker"
            include *
            autoLayout
        }

        component "database" "database" "Double click on + to expand view" {
            title "Data segregated by feature"
            include *
            autoLayout
        }

        component "frontend" "frontend" "Double click on + to expand view" {
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