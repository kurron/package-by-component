# https://github.com/structurizr/dsl/blob/master/docs/language-reference.md

#(Person|SoftwareSystem|Container|Component|DeploymentNode|InfrastructureNode|SoftwareSystemInstance|ContainerInstance|Custom)

!const CODE_NAME "Project Alpha"
!const GROUP_NAME "Group"

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

    !const FOO "Some text you want to reuse."

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
            userFeature = container "User Feature" {
                description "Manages system users"
                technology "Spring Modulith Module"
                tags "tag"
                perspectives {
                }
                userWebUI = component "User Web UI" {
                    description "User management GUI"
                    technology "HTML,JavaScript"
                    perspectives {
                    }
                    adam -> this "manages Users" "JSON over HTTP" "json-over-http" {
                    }
                }
                userServices = component "User Services" {
                    description "User management services"
                    technology "Spring Controller"
                    perspectives {
                    }
                    userWebUI -> this "call user management APIs" "JSON over HTTPS" "son-over-http" {
                    }
                }
            }
            campaignFeature = container "Campaign Feature" {
                description "Manages campaigns"
                technology "Spring Modulith Module"
                tags "tag"
                perspectives {
                }
                campaignWebUI = component "Campaign Web UI" {
                    description "Campaign management GUI"
                    technology "HTML,JavaScript"
                    perspectives {
                    }
                    gary -> this "manages campaigns" "JSON over HTTP" "json-over-http" {
                    }
                }
                campaignServices = component "Campaign Services" {
                    description "Campaign management services"
                    technology "Spring Controller"
                    perspectives {
                    }
                    campaignWebUI -> this "call campaign management APIs" "JSON over HTTPS" "son-over-http" {
                    }
                }
            }
            characterFeature = container "Character Feature" {
                description "Manages characters"
                technology "Spring Modulith Module"
                tags "tag"
                perspectives {
                }
                characterWebUI = component "Character Web UI" {
                    description "Character management GUI"
                    technology "HTML,JavaScript"
                    perspectives {
                    }
                    penny -> this "manages characters" "JSON over HTTP" "json-over-http" {
                    }
                }
                characterServices = component "Character Services" {
                    description "Character management services"
                    technology "Spring Controller"
                    perspectives {
                    }
                    characterWebUI -> this "call character management APIs" "JSON over HTTPS" "son-over-http" {
                    }
                }
            }
            assetFeature = container "Asset Feature" {
                description "Manages character and campaign assets"
                technology "Spring Modulith Module"
                tags "tag"
                perspectives {
                }
                assetWebUI = component "Asset Web UI" {
                    description "Asset management GUI"
                    technology "HTML,JavaScript"
                    perspectives {
                    }
                    adam -> this "manages Assets" "JSON over HTTP" "json-over-http" {
                    }
                }
                assetServices = component "Asset Services" {
                    description "Asset management services"
                    technology "Spring Controller"
                    perspectives {
                    }
                    assetWebUI -> this "call asset management APIs" "JSON over HTTPS" "son-over-http" {
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
                    userServices -> this "read/write user data" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
                component "Campaign Tenant" {
                    description "Campaign information in its own space"
                    technology "MongoDB"
                    perspectives {
                    }
                    campaignServices -> this "read/write campaign data" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
                component "Character Tenant" {
                    description "Character information in its own space"
                    technology "MongoDB"
                    perspectives {
                    }
                    characterServices -> this "read/write user data" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
                    }
                }
                component "Asset Tenant" {
                    description "Asset information in its own space"
                    technology "MongoDB"
                    perspectives {
                    }
                    assetServices -> this "read/write user data" "JSON over MongoDB Wire Protocol" "json-over-mongodb-wire-protocol" {
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
            productionKubernetes = deploymentNode "Kubernetes Cluster" {
                description "On-prem Kubernetes cluster"
                technology "K3S, Rancher"
                deploymentNode "GURPS Pods" {
                    description "On-prem Kubernetes cluster"
                    technology "Kubernetes"
                    instances 8
                    containerInstance userFeature
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

        component "userFeature" "container-user-feature" "Double click on + to expand view" {
            title "Components supporting user management"
            include *
            autoLayout
        }

        component "campaignFeature" "container-campaign-feature" "Double click on + to expand view" {
            title "Components supporting campaign management"
            include *
            autoLayout
        }

        component "characterFeature" "container-character-feature" "Double click on + to expand view" {
            title "Components supporting character management"
            include *
            autoLayout
        }

        component "assetFeature" "container-asset-feature" "Double click on + to expand view" {
            title "Components supporting asset management"
            include *
            autoLayout
        }

        component "database" "database" "Double click on + to expand view" {
            title "Data segregated by feature"
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