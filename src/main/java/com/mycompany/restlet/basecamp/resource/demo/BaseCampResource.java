package com.mycompany.restlet.basecamp.resource.demo;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class BaseCampResource extends ServerResource {

  @Get
  public String getResource()  {
    return "Hello World!";
  }
}
