<#if (actionMessages?exists && actionMessages?size > 0)>
<div<#rt/><#if parameters.cssClass?exists>
 class="${parameters.cssClass?html}"<#rt/>
<#else>
 class="actionMessage"<#rt/>
</#if>
<#if parameters.cssStyle?exists>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
>	
		<#list actionMessages as message>
			<span<#rt/>
>${message}</span><br/>
		</#list>
</div><#rt/>
</#if>