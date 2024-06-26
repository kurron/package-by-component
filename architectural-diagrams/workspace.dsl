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
                    adam -> this "manages available skills, advantages, et al" "JSON over HTTP" "json-over-http" {
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
            userNamespace = container "User Namespace" {
                description "User information in its own space"
                technology "PostgreSQL"
                tags "DataStore"
                perspectives {
                }
                component "Users Table" {
                    description "Individual users"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    userServices -> this "read/write user data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
                component "Group Table" {
                    description "Associate users to groups"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    userServices -> this "read/write group data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
            }
            campaignNamespace = container "Campaign Namespace" {
                description "Campaign information in its own space"
                technology "PostgreSQL"
                tags "DataStore"
                perspectives {
                }
                component "Campaign Table" {
                    description "Campaign information"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    campaignServices -> this "read/write campaign data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
                component "Party Table" {
                    description "Associate characters to campaigns"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    campaignServices -> this "read/write party data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
                component "Asset Table" {
                    description "Associate campaigns to their assets"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    campaignServices -> this "read/write asset data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
            }
            characterNamespace = container "Character Namespace" {
                description "Character information in its own space"
                technology "PostgreSQL"
                tags "DataStore"
                perspectives {
                }
                component "Skills Table" {
                    description "Available character skills"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    characterServices -> this "read/write character data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
                component "Disadvantages Table" {
                    description "Available character disadvantages"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    characterServices -> this "read/write character data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
                component "Advantages Table" {
                    description "Available character advantages"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    characterServices -> this "read/write character data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
                component "Character Table" {
                    description "Character information"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    characterServices -> this "read/write character data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
                component "Asset Table" {
                    description "Associate characters to their acquired assets"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    characterServices -> this "read/write character data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
            }
            equipmentNamespace = container "Equipment Namespace" {
                description "Equipment information in its own space"
                technology "PostgreSQL"
                tags "DataStore"
                perspectives {
                }
                component "Firearms Table" {
                    description "Firearm descriptions"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    assetServices -> this "read/write asset data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
                component "Ranged Weapons Table" {
                    description "Ranged weapons descriptions"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    assetServices -> this "read/write asset data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
                component "Melee Weapons Table" {
                    description "Melee weapons descriptions"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    assetServices -> this "read/write asset data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
                component "Armor Table" {
                    description "Armor descriptions"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    assetServices -> this "read/write asset data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
                component "Shield Table" {
                    description "Shield descriptions"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    assetServices -> this "read/write asset data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
                characterEquipmentTable = component "Character Table" {
                    description "Equipment for character use"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    assetServices -> this "read/write asset data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
                component "Campaign Table" {
                    description "Equipment for campaign use"
                    technology "PostgreSQL"
                    perspectives {
                    }
                    assetServices -> this "read/write asset data" "Spring Data JDBC" "jdbc-driver" {
                    }
                }
            }
        }

        production = deploymentEnvironment "production" {
            deploymentNode "PostgreSQL Cluster" {
                description "Fault tolerant cluster"
                technology "Hosted PostgreSQL"
                deploymentNode "Campaign Namespace" {
                    containerInstance campaignNamespace
                }
                deploymentNode "Equipment Namespace" {
                    containerInstance equipmentNamespace
                }
                deploymentNode "Character Namespace" {
                    containerInstance characterNamespace
                }
                deploymentNode "User Namespace" {
                    containerInstance userNamespace
                }
            }
            productionKubernetes = deploymentNode "Kubernetes Cluster" {
                description "On-prem Kubernetes cluster"
                technology "K3S, Rancher"
                deploymentNode "GURPS Pods" {
                    description "On-prem Kubernetes cluster"
                    technology "Kubernetes"
                    instances 2
                    deploymentNode "User Feature" {
                        containerInstance userFeature
                    }
                    deploymentNode "Campaign Feature" {
                        containerInstance campaignFeature
                    }
                    deploymentNode "Character Feature" {
                        containerInstance characterFeature
                    }
                    deploymentNode "Asset Feature" {
                        containerInstance assetFeature
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
            relationship "jdbc-driver" {
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

        component "userNamespace" "container-user-namespace" "Double click on + to expand view" {
            title "Tables within the namespace"
            include *
            autoLayout
        }

        component "campaignNamespace" "container-campaign-namespace" "Double click on + to expand view" {
            title "Tables within the namespace"
            include *
            autoLayout
        }

        component "characterNamespace" "container-character-namespace" "Double click on + to expand view" {
            title "Tables within the namespace"
            include *
            autoLayout
        }

        component "equipmentNamespace" "container-asset-namespace" "Double click on + to expand view" {
            title "Tables within the namespace"
            include *
            autoLayout
        }

        deployment "*" "production" "deployment-production" "Production deployment overview" {
            title "Production Deployment Diagram"
            description "Some instances are hosted off-site"
            include *
            autoLayout
        }
        dynamic "assetFeature" "adam-bulk-character-asset-flow" "Click on button to animate" {
            title "Bulk character asset processing flow"
            autoLayout lr

#            <element identifier> -> <element identifier> [description] [technology]
#            <relationship identifier> [description]
             adam -> assetWebUI "upload file of new character assets"
             assetWebUI -> assetServices "forwards the file for processing"
             assetServices -> characterEquipmentTable "inserts or updates assets"
        }
    }
}