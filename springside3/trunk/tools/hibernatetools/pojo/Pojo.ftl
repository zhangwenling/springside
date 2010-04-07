${pojo.getPackageDeclaration()}

<#assign classbody>
<#include "PojoTypeDeclaration.ftl"/> {

<#include "PojoFields.ftl"/>

<#include "PojoConstructors.ftl"/>
   
<#include "PojoPropertyAccessors.ftl"/>
}
</#assign>

${pojo.generateImports()}
${classbody}

