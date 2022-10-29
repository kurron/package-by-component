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
                description "Web server"
                technology "Apache Tomcat"
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
            }
            cli = container "Command Line Interface" {
                description "Command line user interface"
                technology "Spring, Java"
                perspectives {
                }
                this -> messageBroker "sends command messages to" "JSON over Pulsar Protocol" "TAG" {
                }
            }
            eventProcessor = container "Event Processor" {
                description "Reacts to events"
                technology "Spring, Java"
                perspectives {
                }
                storageCommandExecutor = component "Storage Command Executor" {
                    description "Executes storage update commands "
                    technology "Apache Pulsar, Java"
                    perspectives {
                    }
                    this -> readStore "GURPS document" "Spring Data MongoDB" "TAG" {
                    }
                }
                messagingPortEvent = component "Messaging Port (events)" {
                    description "Accepts events messages, converting them into storage update commands"
                    technology "Apache Pulsar, Java"
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
                technology "Spring, Java"
                perspectives {
                }
                commandExecutor = component "Command Executor" {
                    description "Executes GURPS commands "
                    technology "Apache Pulsar, Java"
                    perspectives {
                    }
                    this -> messageBroker "publish events of completed commands" "GURPS events" "TAG" {
                    }
                    this -> writeStore "GURPS document" "Spring Data MongoDB" "TAG" {
                    }
                }
                messagingPortCommand = component "Messaging Port (commands)" {
                    description "Accepts command messages, converting them into GURPS commands"
                    technology "Apache Pulsar, Java"
                    perspectives {
                    }
                    messageBroker -> this "sends command messages to" "JSON over Pulsar Protocol" "TAG" {
                    }
                    this -> commandExecutor "sends commands for execution" "Java Records" "TAG" {
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
    }
}