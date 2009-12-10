<#if (actionMessages?exists && actionMessages?size > 0)>
	
		<#list actionMessages as message>
			<span<#rt/>
<#if parameters.cssClass?exists>
 class="${parameters.cssClass?html}"<#rt/>
<#else>
 class="actionMessage"<#rt/>
</#if>
<#if parameters.cssStyle?exists>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
>${message}</span><br/>
		</#list>

</#if>