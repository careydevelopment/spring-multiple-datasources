# spring-multiple-datasources
This project includes a fully deployable Spring Boot app that uses multiple data sources.

It accepts a single POST call that includes customer info. Then, it persists that info and returns a new Customer object with the ID of the persisted data.

If you want to know more, you should follow along with <a href="https://careydevelopment.us/2019/01/18/how-to-configure-multiple-data-sources-spring-boot/" target="_blank">the tutorial.</a>

To use the app, you'll need to reconfigure the MultiTenantManager class and the DataSourceConfig class to include specifics about your own MySQL database tables.

Once you've done that, just pull this project down in Eclipse. Then, you can run it by right-clicking on CustomerManagementApplication and selecting Run As... Java Application.

Once the application is launched, the endpoints for the POST request are:

http://localhost:8090/app/fishingsupplies/customer

or

http://localhost:8090/app/gadgetwarehouse/customer

Note that you'll need to include a request body that looks something like this:
{
"firstName" : "Joe",
"lastName" : "Blow"
}