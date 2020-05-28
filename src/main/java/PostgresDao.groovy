import groovy.sql.Sql

class PostgresDao {

    public static void main(String[] args) {
        def dbUrl = "jdbc:postgresql://localhost/itmo"
        def dbUser = "postgres"
        def dbPassword = "mysecurepassword"
        def dbDriver = "org.postgresql.Driver"

        def sql = Sql.newInstance(dbUrl, dbUser, dbPassword, dbDriver)

        println "All Records:"
        def query = "select * from item"
        def counter = 0
        sql.eachRow query, {item->
            counter ++
        }
        println counter
    }

}