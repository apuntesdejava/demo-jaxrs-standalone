/*
 * Copyright 2019 Diego Silva Limaco <diego.silva at apuntesdejava.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apuntesdejava.jaxrs.server.endpoint;

import com.apuntesdejava.jaxrs.server.domain.Person;
import com.apuntesdejava.jaxrs.server.endpoint.param.PersonParam;
import com.apuntesdejava.jaxrs.server.repository.PersonRepository;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;

/**
 *
 * @author Diego Silva Limaco <diego.silva at apuntesdejava.com>
 */
@Path("person")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@ApplicationScoped
public class PersonEndpoint {

    @Inject
    private PersonRepository personRepository;

    @POST
    public Response create(PersonParam param) {
        Person p = personRepository.create(param.getName(), param.getEmail());
        return Response.ok(p).build();
    }

    @GET
    public Response list() {
        List<Person> list = personRepository.findAll();
        return Response.ok(list).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id")long personId){
        personRepository.delete(personId);
        return Response.ok().build();
    }

}
