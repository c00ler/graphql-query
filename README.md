# GraphQL Query

## GraphQL UI

`http://localhost:8080/graphiql`

## Sample query

```graphql
{
  users {
    id
  }
  user(id: "8c893ca9-21d3-4986-b863-f558c6d70bae") {
    id
    firstName
    lastName
    address {
      addressLine
      city
      zipCode
    }
  }
}
```
