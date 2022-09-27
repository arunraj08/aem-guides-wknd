package com.adobe.aem.guides.wknd.core.workflow;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceVendor;
import org.osgi.service.component.propertytypes.ServiceDescription;

@Component(service = WorkflowProcess.class, property = { "process.label= My Sample Workflow Process" })
@ServiceDescription(value = "A sample workflow process implementation")
@ServiceVendor(value = "Red")
public class MyProcess implements WorkflowProcess {
    private static final String TYPE_JCR_PATH = "JCR_PATH";

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {
        WorkflowData workflowData = workItem.getWorkflowData();

        if (workflowData.getPayloadType().equals(TYPE_JCR_PATH)) {
            String pagePath = workflowData.getPayload().toString() + "/jcr:content";            
            try {
                final ResourceResolver resolver = workflowSession.adaptTo(ResourceResolver.class);
                final Resource resource = resolver != null ? resolver.getResource(pagePath) : null;
                
                if (resource != null) {
                    ModifiableValueMap modifiableValueMap = resource.adaptTo(ModifiableValueMap.class);
                    if (modifiableValueMap != null) {
                        modifiableValueMap.put("approved", readArgument(args));
                    }
                    resolver.commit();
                }
            } catch (PersistenceException e) {
                throw new WorkflowException(e.getMessage(), e);
            }
        
        }

    }

    private boolean readArgument(MetaDataMap args) {
        String argument = args.get("PROCESS_ARGS", "false");
        return argument.equalsIgnoreCase("true");
    }

}