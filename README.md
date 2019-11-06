# demo-jaxrs-standalone
Ejemplo de un cliente de JAX-RS Standalone 

Este proyecto tiene dos módulos para que pueda ser funcional el ejemplo.

- [demo-jaxrs-server](demo-jaxrs-server) Es un servidor usando Payara Micro que se debe ejecutar primero
- [demo-jaxrs-client](demo-jaxrs-client) Es el cliente que invocará al server. Este utiliza la biblioteca Apache CXF para invocar al endpoint