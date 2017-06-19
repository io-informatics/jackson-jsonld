package ioinformarics.oss.jackson.module.jsonld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldLink;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldPropertyType;

/**
 * @author Alexander De Leon
 */
public class JsonldGraphBuilder<T> {

    protected String context;
    protected String graphType;
    protected String graphId;
    protected JsonldResourceBuilder<T> resourceBuilder;
    protected Function<T, String> typeSupplier;

    //<id>,<type>
    private Map<String,List<String>> alreadyExistingProperty = new HashMap<String, List<String>>();
    
    JsonldGraphBuilder() {
        resourceBuilder = new JsonldResourceBuilder<T>();
    }

    public JsonldGraphBuilder<T> context(String context){
        this.context = context;
        return this;
    }

    public JsonldGraphBuilder<T> type(String type){
        this.graphType = type;
        return this;
    }

    public JsonldGraphBuilder<T> id(String id){
        this.graphId = id;
        return this;
    }

    public JsonldGraphBuilder<T> elementId(Function<T,String> idSupplier){
        this.resourceBuilder.id(idSupplier);
        return this;
    }

    public JsonldGraphBuilder<T> elementType(Function<T,String> typeSupplier){
        this.typeSupplier = typeSupplier;
        return this;
    }

    public JsonldResource build(Iterable<T> elements) {
        Optional<JsonNode> generatedContext = JsonldContextFactory.multiContext(Optional.ofNullable(context), JsonldContextFactory.fromAnnotations(elements));
        return new JsonldGraph(buildElements(elements), generatedContext.orElse(null), graphType, graphId);

    }

    protected String getType(T e) {
        return typeSupplier.apply(e);
    }

    protected Iterable<JsonldResource> buildElements(Iterable<T> elements) {
        ArrayList<JsonldResource> list = new ArrayList<>();
        elements.forEach(e -> {
            JsonldResourceBuilder builder = JsonldResource.Builder.create();
            builder.type(resourceBuilder.getType(e));
            builder.id(resourceBuilder.getId(e));
            builder.context(null);
            //Build it here
            list.addAll(getDatatypesOnly(e));
            
            list.add(builder.build(e, true));
        });
        return list;
    }

    protected List<JsonldResource> getDatatypesOnly(T element)
    {
    	
    	List<JsonldResource> listJsonLdResources = new ArrayList<JsonldResource>();
    	
    	  Arrays.asList(((Object) element).getClass().getDeclaredFields()).forEach( field -> {
          	JsonldPropertyType jsonldPropertyType = field.getAnnotation(JsonldPropertyType.class);

          	//FIXME: this is a hack!
              if(jsonldPropertyType != null && ( jsonldPropertyType.type().contains("owl:Datatype") || jsonldPropertyType.type().contains("rdfs:"))){
            	  
            	  List<String> typesList = alreadyExistingProperty.get(jsonldPropertyType.id());
            	  if(typesList != null)
            	  {
            		  if(!typesList.contains(jsonldPropertyType.type()))
            		  {
            			  alreadyExistingProperty.get(jsonldPropertyType.id()).add(jsonldPropertyType.type());
            			  JsonldResource jsonldR = new BeanJsonldResource(null, null, jsonldPropertyType.type(), jsonldPropertyType.id());
            			  listJsonLdResources.add(jsonldR);
            		  }
            	  }
            	  else
            	  {
            		  List<String> types = new ArrayList<String>();
            		  types.add(jsonldPropertyType.type());
            		  alreadyExistingProperty.put(jsonldPropertyType.id(), types);
            		  JsonldResource jsonldR = new BeanJsonldResource(null, null, jsonldPropertyType.type(), jsonldPropertyType.id());
        			  listJsonLdResources.add(jsonldR);
            	  }
            	 
              }
          });
    	  
    	  //the jsonldPropertyType used in a class level
          JsonldLink[] jsonldLinks = ((Object) element).getClass().getAnnotationsByType(JsonldLink.class);
          if(jsonldLinks != null){
              for(int i=0; i < jsonldLinks.length; i++){
            	  if(jsonldLinks[i] != null && (jsonldLinks[i].name().contains("owl:Datatype") || jsonldLinks[i].name().contains("rdfs:"))){
                	  
                	  List<String> typesList = alreadyExistingProperty.get(jsonldLinks[i].rel());
                	  if(typesList != null)
                	  {
                		  if(!typesList.contains(jsonldLinks[i].name()))
                		  {
                			  alreadyExistingProperty.get(jsonldLinks[i].rel()).add(jsonldLinks[i].name());
                			  JsonldResource jsonldR = new BeanJsonldResource(null, null, jsonldLinks[i].name(), jsonldLinks[i].rel());
                			  listJsonLdResources.add(jsonldR);
                		  }
                	  }
                	  else
                	  {
                		  List<String> types = new ArrayList<String>();
                		  types.add(jsonldLinks[i].name());
                		  alreadyExistingProperty.put(jsonldLinks[i].rel(), types);
                		  JsonldResource jsonldR = new BeanJsonldResource(null, null, jsonldLinks[i].name(), jsonldLinks[i].rel());
            			  listJsonLdResources.add(jsonldR);
                	  }
                  }
              }
          }
    	  
    	  //do it for the links as well
    	return listJsonLdResources;
    }

}
