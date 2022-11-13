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
            writeStore = container "Write Store" {
                description "Persists current state of the system, write-mostly"
                technology "MongoDB"
                tags "DataStore"
                perspectives {
                }
                inProgressCampaigns = component "Campaign Collection" {
                    description "Stores in-progress campaigns"
                    technology "MongoDB"
                    perspectives {
                    }
                }
                inProgressCharacters = component "Character Collection" {
                    description "Stores in-progress characters"
                    technology "MongoDB"
                    perspectives {
                    }
                }
            }
            readStore = container "Read Store" {
                description "Eventually consistent state of the system, read-only"
                technology "MongoDB"
                tags "DataStore"
                perspectives {
                }
                completedCampaigns = component "Campaign Collection" {
                    description "Stores completed campaigns"
                    technology "MongoDB"
                    perspectives {
                    }
                }
                completedCharacters = component "Character Collection" {
                    description "Stores completed characters"
                    technology "MongoDB"
                    perspectives {
                    }
                }
                component "Users Collection" {
                    description "Stores system users"
                    technology "MongoDB"
                    perspectives {
                    }
                }
            }
            messageBroker = container "Message Broker" {
                description "Messaging fabric"
                technology "RabbitMQ"
                tags "MessageBroker"
                perspectives {
                }
                commandExchange = component "Command Exchange" {
                    description "Command messages get published to here"
                    technology "RabbitMQ"
                    perspectives {
                    }
                }
                eventExchange = component "Event Exchange" {
                    description "Event messages get published to here"
                    technology "RabbitMQ"
                    perspectives {
                    }
                }
                component "Dead Letter Exchange" {
                    description "Undeliverable messages get published to here"
                    technology "RabbitMQ"
                    perspectives {
                    }
                }
            }
            webServer = container "Web Server" {
                description "HTTP server"
                technology "Kotlin, Spring Boot, Apache Tomcat"
                perspectives {
                }
                this -> messageBroker "sends commands to" "JSON over AMQP" "AMQP" {
                }
            }
            gui = container "Web User Interface" {
                description "Graphical user interface"
                technology "HTML, Javascript"
                tags "WebUI"
                perspectives {
                }
                this -> webServer "sends commands to" "JSON over HTTP" "TAG" {
                }
                this -> webServer "sends queries to" "JSON over HTTP" "TAG" {
                }
                gary -> this "creates campaigns" "JSON over HTTP" "TAG" {
                }
                penny -> this "creates characters" "JSON over HTTP" "TAG" {
                }
            }
            cli = container "Command Line Interface" {
                description "Command line user interface"
                technology "Kotlin, Spring Shell"
                tags "CommandLine"
                perspectives {
                }
                adam -> this "uses" "typed commands" "TAGE" {
                }
                this -> commandExchange "sends commands to" "JSON over AMQP" "AMQP" {
                }
            }
            queryProcessor = container "Query Processor" {
                description "Handles requests for information"
                technology "Kotlin, Spring Boot"
                tags "Microservice"
                perspectives {
                }
                queryExecutor = component "Query Executor" {
                    description "Fetches information from the database"
                    technology "Kotlin, Spring Data MongoDB"
                    perspectives {
                    }
                    this -> completedCampaigns "reads documents from" "MongoDB's BSON protocol" "BSON" {
                    }
                    this -> completedCharacters "reads documents from" "MongoDB's BSON protocol" "BSON" {
                    }
                }
                graphQL = component "GraphQL Handler" {
                    description "Accepts GraphQL queries"
                    technology "Kotlin, Spring GraphQL"
                    perspectives {
                    }
                    this -> queryExecutor "GraphQL request" "CURRENTLY UNKNOWN" "TAG" {
                    }
                    webServer -> this "sends queries to" "GraphQL over HTTP" "TAG" {
                    }
                    cli -> this "sends queries to" "GraphQL over HTTP" "TAG" {
                    }
                }
            }
            eventProcessor = container "Event Processor" {
                description "Reacts to events"
                technology "Kotlin, Spring Boot, Spring Integration"
                tags "Microservice"
                perspectives {
                }
                storageCommandExecutor = component "Storage Command Executor" {
                    description "Executes storage update commands "
                    technology "Kotlin, Spring Data MongoDB"
                    perspectives {
                    }
                    this -> completedCampaigns "saves document to" "MongoDB's BSON protocol" "BSON" {
                    }
                    this -> completedCharacters "saves document to" "MongoDB's BSON protocol" "BSON" {
                    }
                }
                messagingPortEvent = component "Messaging Port (events)" {
                    description "Accepts events messages, converting them into storage update commands"
                    technology "Kotlin, Spring Integration"
                    perspectives {
                    }
                    eventExchange -> this "sends events to" "JSON over AMQP" "AMQP" {
                    }
                    this -> storageCommandExecutor "sends storage commands to" "direct call" "TAG" {
                    }
                }
            }
            commandProcessor = container "Command Processor" {
                description "Executes commands"
                technology "Kotlin, Spring Boot, Spring Integration"
                tags "Microservice"
                perspectives {
                }
                eventPort = component "Event Port" {
                    description "Publishes events to the message broker"
                    technology "Kotlin, Spring Integration"
                    tags "Channel Adapter"
                    perspectives {
                    }
                    this -> eventExchange "publishes events to" "JSON over AMQP" "AMQP" {
                    }
                }
                eventChannel = component "Event Channel" {
                    description "Propagates events downstream to other components"
                    technology "Kotlin, Spring Integration"
                    tags "Channel"
                    perspectives {
                    }
                    this -> eventPort "sends commands to" "Spring Integration" "TAG" {
                    }
                }
                administrationCommandExecutor = component "Administration Command Executor" {
                    description "Executes GURPS commands related to system administration"
                    technology "Kotlin, Spring Integration"
                    perspectives {
                    }
                    this -> eventChannel "sends events to" "via messaging channel" "TAG" {
                    }
                }
                campaignCommandExecutor = component "Campaign Command Executor" {
                    description "Executes GURPS commands related to campaigns"
                    technology "Kotlin, Spring Integration"
                    perspectives {
                    }
                    this -> eventChannel "sends events to" "via messaging channel" "TAG" {
                    }
                    this -> inProgressCampaigns "sends campaign changes to" "MongoDB's BSON protocol" "BSON" {
                    }
                }
                characterCommandExecutor = component "Character Command Executor" {
                    description "Executes GURPS commands related to characters"
                    technology "Kotlin, Spring Integration"
                    perspectives {
                    }
                    this -> eventChannel "sends events to" "via messaging channel" "TAG" {
                    }
                    this -> inProgressCharacters "sends character changes to" "MongoDB's BSON protocol" "BSON" {
                    }
                }
                commandChannel = component "Command Channel" {
                    description "Propagates commands downstream to other components"
                    technology "Kotlin, Spring Integration"
                    tags "Channel"
                    perspectives {
                    }
                    this -> campaignCommandExecutor "sends campaign commands to" "Spring Integration" "TAG" {
                    }
                    this -> characterCommandExecutor "sends character commands to" "Spring Integration" "TAG" {
                    }
                    this -> administrationCommandExecutor "sends administration commands to" "Spring Integration" "TAG" {
                    }
                }
                commandPort = component "Command Port" {
                    description "Accepts command messages, converting them into GURPS commands"
                    technology "Kotlin, Spring Integration"
                    tags "Channel Adapter"
                    perspectives {
                    }
                    commandExchange -> this "sends commands to" "JSON over AMQP" "AMQP" {
                    }
                    this -> commandChannel "sends commands to" "via messaging channel" "TAG" {
                    }
                }
            }
        }
        production = deploymentEnvironment "production" {
            deploymentNode "MongoDB Cluster" {
                description "MongoDB fault tolerant cluster"
                technology "Hosted MongoDB"
                containerInstance writeStore
                containerInstance readStore
            }
            deploymentNode "RabbitMQ Cluster" {
                description "RabbitMQ fault tolerant cluster"
                technology "Hosted RabbitMQ"
                containerInstance messageBroker
            }
            productionKubernetes = deploymentNode "Kubernetes Cluster" {
                description "On-prem Kubernetes cluster"
                technology "K3S, Rancher"
                webServerPods = deploymentNode "Web Server Pods" {
                    description "On-prem Kubernetes cluster"
                    technology "Kubernetes"
                    instances 4
                    containerInstance webServer {
                        description "some description"
                        healthCheck "some-name" "http://example.com/health" 60 60
                    }
                }
                deploymentNode "Command Processor Pods" {
                    description "On-prem Kubernetes cluster"
                    technology "Kubernetes"
                    instances 8
                    containerInstance commandProcessor {
                        description "some description"
                        healthCheck "some-name" "http://example.com/health" 60 60
                    }
                }
                deploymentNode "Event Processor Pods" {
                    description "On-prem Kubernetes cluster"
                    technology "Kubernetes"
                    instances 8
                    containerInstance eventProcessor {
                        description "some description"
                        healthCheck "some-name" "http://example.com/health" 60 60
                    }
                }
                deploymentNode "Query Processor Pods" {
                    description "On-prem Kubernetes cluster"
                    technology "Kubernetes"
                    instances 16
                    containerInstance queryProcessor {
                        description "some description"
                        healthCheck "some-name" "http://example.com/health" 60 60
                    }
                }
            }
            deploymentNode "Firewall" {
                description "Internet Gateway"
                technology "Some fancy technology"
                instances 8
                gateway = infrastructureNode "Internet Gateway" "Controls access from the internet" "Some fancy technology" "TAG" {
                    gateway -> webServerPods "sends web traffic to" "HTTPS" {
                    }
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

            relationship "HTTP" {
            }
            relationship "AMQP" {
                thickness 2
                style dashed
                color #E8A87C
            }
            relationship "BSON" {
                thickness 2
                style dashed
                color #E27D60
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
            autoLayout lr
        }

        component "commandProcessor" "command-processor" "Double click on + to expand view" {
            title "Command Processor"
            include *
            autoLayout lr
        }

        component "eventProcessor" "event-processor" "Double click on + to expand view" {
            title "Event Processor"
            include *
            autoLayout lr 
        }

        component "queryProcessor" "query-processor" "Double click on + to expand view" {
            title "Query Processor"
            include *
            autoLayout lr
        }

        component "writeStore" "write-store" "Double click on + to expand view" {
            title "Write Store"
            include *
            autoLayout lr
        }

        component "readStore" "read-store" "Double click on + to expand view" {
            title "Read Store"
            include *
            autoLayout
        }

        component "messageBroker" "message-broker" "Double click on + to expand view" {
            title "Message Broker"
            include *
            autoLayout
        }

        deployment "*" "production" "deployment-production" "Production deployment overview" {
            title "Production Deployment Diagram"
            description "Some instances are hosted off-site"
            include *
            autoLayout
        }
    }
}