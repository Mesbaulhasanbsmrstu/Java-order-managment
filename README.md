# Sea Fish Supply System - Java

Spring Boot MVC version of the original ASP.NET Core MVC order management app.

## Open in IntelliJ

1. Open IntelliJ IDEA.
2. Choose `File > Open`.
3. Select this folder: `G:\codex-project\Order Management Java`.
4. Open it as a Maven project.
5. Run `OrderManagementJavaApplication`.

## Run

If Maven is available in your terminal:

```powershell
mvn spring-boot:run
```

The app starts on:

```text
http://localhost:8080
```

## Database

This Java version is configured to use the same SQL Server database as the
ASP.NET Core app:

```text
Server=Mesba
Database=SeaFishSupplyDb
Windows trusted authentication
```

For Maven runs, the project loads Microsoft's `mssql-jdbc_auth` DLL from the
local Maven repository. If you run the main class directly from IntelliJ and
Windows authentication fails, add this VM option to the run configuration:

```text
-Djava.library.path=C:\Users\Hp\.m2\repository\com\microsoft\sqlserver\mssql-jdbc_auth\12.6.4.x64
```

If you prefer SQL Server username/password authentication, replace the
`spring.datasource.*` values in `src/main/resources/application.properties`.
