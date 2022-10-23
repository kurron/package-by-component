# https://github.com/structurizr/dsl/blob/master/docs/language-reference.md

#(Person|SoftwareSystem|Container|Component|DeploymentNode|InfrastructureNode|SoftwareSystemInstance|ContainerInstance|Custom)

!constant CODE_NAME "Project Alpha"
!constant GROUP_NAME "Group"

/*
multi line
*/

# single line
// single line

workspace "Overstock Citi Integration" "Overstock Citi integration" {
    !identifiers flat
    !impliedRelationships true
   #!include <file|directory|url>

    !docs docs
    !adrs adrs

    !constant FOO "Some text you want to reuse."

    model {}