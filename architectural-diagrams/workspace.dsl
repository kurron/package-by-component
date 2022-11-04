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
                perspectives {
                }
            }
            readStore = container "Read Store" {
                description "Eventually consistent state of the system, read-only"
                technology "MongoDB"
                perspectives {
                }
            }
            messageBroker = container "Message Broker" {
                description "Messaging fabric"
                technology "Apache Pulsar"
                perspectives {
                }
            }
            webServer = container "Web Server" {
                description "HTTP server"
                technology "Kotlin, Spring Boot, Apache Tomcat"
                perspectives {
                }
                this -> messageBroker "sends command messages to" "JSON over Pulsar Protocol" "TAG" {
                }
            }
            gui = container "Web User Interface" {
                description "Graphical user interface"
                technology "HTML, Javascript"
                perspectives {
                }
                this -> webServer "sends command messages to" "JSON over HTTP" "TAG" {
                }
                gary -> this "creates campaigns" "JSON over HTTP" "TAG" {
                }
                penny -> this "creates characters" "JSON over HTTP" "TAG" {
                }
            }
            cli = container "Command Line Interface" {
                description "Command line user interface"
                technology "Kotlin, Spring Boot"
                perspectives {
                }
                adam -> this "sends command messages to" "JSON over Pulsar Protocol" "TAG" {
                }
                this -> messageBroker "sends command messages to" "JSON over Pulsar Protocol" "TAG" {
                }
            }
            queryProcessor = container "Query Processor" {
                description "Handles requests for information"
                technology "Kotlin, Spring Boot"
                perspectives {
                }
                queryExecutor = component "Query Executor" {
                    description "Fetches information from the database"
                    technology "Kotlin, Spring Data MongoDB"
                    perspectives {
                    }
                    this -> readStore "GURPS document" "MongoDB's BSON protocol" "TAG" {
                    }
                }
                graphQL = component "GraphQL Handler" {
                    description "Accepts GraphQL queries"
                    technology "Kotlin, Spring GraphQL"
                    perspectives {
                    }
                    this -> queryExecutor "GraphQL request" "CURRENTLY UNKNOWN" "TAG" {
                    }
                    webServer -> this "GraphQL request" "HTTP" "TAG" {
                    }
                    cli -> this "GraphQL request" "HTTP" "TAG" {
                    }
                }
            }
            eventProcessor = container "Event Processor" {
                description "Reacts to events"
                technology "Kotlin, Spring Boot"
                perspectives {
                }
                storageCommandExecutor = component "Storage Command Executor" {
                    description "Executes storage update commands "
                    technology "Kotlin, Spring Pulsar"
                    perspectives {
                    }
                    this -> readStore "GURPS document" "Spring Data MongoDB" "TAG" {
                    }
                }
                messagingPortEvent = component "Messaging Port (events)" {
                    description "Accepts events messages, converting them into storage update commands"
                    technology "Kotlin, Spring Pulsar"
                    perspectives {
                    }
                    messageBroker -> this "sends events of completed commands" "GURPS events" "TAG" {
                    }
                    this -> storageCommandExecutor "sends storage commands for execution" "Java Records" "TAG" {
                    }
                }
            }
            commandProcessor = container "Command Processor" {
                description "Executes commands"
                technology "Kotlin, Spring Boot"
                perspectives {
                }
                commandExecutor = component "Command Executor" {
                    description "Executes GURPS commands "
                    technology "Kotlin, Spring Pulsar, Spring Data MongoDB"
                    perspectives {
                    }
                    this -> messageBroker "publish events of completed commands" "GURPS events" "TAG" {
                    }
                    this -> writeStore "GURPS document" "MongoDB's BSON protocol" "TAG" {
                    }
                }
                messagingPortCommand = component "Messaging Port (commands)" {
                    description "Accepts command messages, converting them into GURPS commands"
                    technology "Kotlin, Spring Pulsar"
                    perspectives {
                    }
                    messageBroker -> this "sends command messages to" "JSON over Pulsar Protocol" "TAG" {
                    }
                    this -> commandExecutor "sends commands for execution" "Java Records" "TAG" {
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
            deploymentNode "Apache Pulsar Cluster" {
                description "Pulsar fault tolerant cluster"
                technology "Hosted Apache Pulsar"
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


    views {
        theme default
        styles {
            element "DataStore" {
                shape Cylinder
            }
            element "Cron" {
                shape Robot
            }
            element "FileStore" {
                shape Folder
                background #dac292
            }
            relationship "HTTP" {
            }
            relationship "Pulsar" {
            }
        }

        systemContext "gurps" "system-context" "50,000 foot view of the system and its collaborators" {
            title "High level view of the solution"
            include *
            autoLayout
        }

        container "gurps" "container-gurps" "SOMETHING MEANINGFUL SHOULD GO HERE" {
            title "More detailed view of the solution"
            include *
            autoLayout
        }

        component "commandProcessor" "command-processor" "SOMETHING MEANINGFUL SHOULD GO HERE" {
            title "Something meaningful should go here"
            include *
            autoLayout
        }

        component "eventProcessor" "event-processor" "SOMETHING MEANINGFUL SHOULD GO HERE" {
            title "Something meaningful should go here"
            include *
            autoLayout
        }

        component "queryProcessor" "query-processor" "SOMETHING MEANINGFUL SHOULD GO HERE" {
            title "Something meaningful should go here"
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