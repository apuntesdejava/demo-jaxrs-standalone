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
package com.apuntesdejava.jaxrs.client;

import com.apuntesdejava.jaxrs.client.domain.Person;
import com.apuntesdejava.jaxrs.client.endpoint.PersonEndpoint;
import com.apuntesdejava.jaxrs.client.endpoint.param.PersonParam;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 *
 * @author Diego Silva Limaco <diego.silva at apuntesdejava.com>
 */
public class App {

    private static final Logger LOG = Logger.getLogger(App.class.getName());
    private static final String REST_URI = "http://localhost:8080/api";

    public static void main(String[] args) {
        PersonEndpoint client = JAXRSClientFactory.create(
                REST_URI,
                PersonEndpoint.class,
                Arrays.asList(
                        new JacksonJaxbJsonProvider()
                ));

        LOG.info("=== insertando un registro:");
        PersonParam param = new PersonParam("persona 1", "abc@mail.com");
        Response resp = client.create(param);
        LOG.log(Level.INFO, "status:{0}", resp.getStatusInfo().getReasonPhrase());
        if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
            Person p = resp.readEntity(Person.class);
            LOG.log(Level.INFO, "-> registro insertado:{0}", p.toString());
        }

        LOG.info("== Listando los registros:");
        resp = client.list();
        LOG.log(Level.INFO, "status:{0}", resp.getStatusInfo().getReasonPhrase());
        List<Person> list = null;
        if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
            list = resp.readEntity(new GenericType<List<Person>>() {
            });
            list.forEach((p) -> {
                LOG.log(Level.INFO, "id:{0}\tname:{1}\temail:{2}", new Object[]{p.getPersonId(), p.getName(), p.getEmail()});
            });
        }

        if (list != null) {
            LOG.info("== borrando los registros");
            list.forEach((p) -> client.delete(p.getPersonId()));
        }
    }
}
