package bot.storages;

import bot.annotations.Processing;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Set;

@Configuration
public class ActionHandlersStorage {

    @Autowired
    private ApplicationContext context;

    private HashMap<Action, BaseHandler> actionsMap;

    public BaseHandler getHandler(Action action){
        return actionsMap.get(action);
    }

    @PostConstruct
    public void init() throws Exception {
        AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
        actionsMap=new HashMap<>();

        ClassPathScanningCandidateComponentProvider provider
                = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Processing.class));

        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents("bot");
        for(BeanDefinition beanDefinition : beanDefinitions){
            Class<? extends BaseHandler> clazz = (Class<? extends BaseHandler>) Class.forName(beanDefinition.getBeanClassName());
            Processing processAnnotation = clazz.getAnnotation(Processing.class);

            Action neededAction=processAnnotation.value();
            if(actionsMap.containsKey(neededAction))
                throw new Exception("this action is already being process");

            BaseHandler handler = beanFactory.createBean(clazz);
            handler.setProcessingAction(neededAction);
            actionsMap.put(neededAction, handler);
        }
    }

}
