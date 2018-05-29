import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

        DeploymentBuilder deployment = engine.getRepositoryService().createDeployment();

        //engine.getRepositoryService().createDeploymentQuery()
        ProcessDefinition id = engine.getRepositoryService().createProcessDefinitionQuery().deploymentId("id").singleResult();


        IdentityService identityService = engine.getIdentityService();


        List<Group> list = identityService.createGroupQuery().list();
        list.stream().forEach(System.out::println);

        System.out.println("-------------------------------------");

        List<Group> groups = identityService.createGroupQuery().listPage(1, 5);
        groups.stream().forEach(System.out::println);

//        for(int i=0; i<10; i++) {
//            Group group = identityService.newGroup("group"+i);
//            group.setName("name"+i);
//            group.setType("type"+i);
//            identityService.saveGroup(group);
//
//        }
        System.out.println("-------------------------------------");
        long count = identityService.createGroupQuery().groupNameLike("name1%").count();
        System.out.println("count = " + count);

        List<Group> list1 = identityService.createGroupQuery().orderByGroupName().desc().list();
        list1.stream().forEach(System.out::println);

        identityService.createGroupQuery().orderByGroupName().desc()
                                            .orderByGroupId().asc().list();

        System.out.println("-------------------------------------");
        //Call Native SQL
        Group group = identityService.createNativeGroupQuery().sql("select * from act_id_group where NAME_ = #{name}")
                .parameter("name", "name2").singleResult();

        System.out.println("group = " + group.getName());

        engine.close();
        System.exit(0);

    }
}
