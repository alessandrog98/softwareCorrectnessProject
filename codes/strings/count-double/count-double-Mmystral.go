//BEGIN
package main

import (
"fmt"
"strings"
)

func main() {
var s string
fmt.Print("Enter a string (max 50 characters): ")
fmt.Scanln(&s)

s = strings.ToLower(s)
fmt.Println("Lowercase string:", s)

for i := 0; i < len(s)-1; i++ {
    if s[i] == s[i+1] {
        fmt.Println("The string contains doubles.")
        return
    }
}

fmt.Println("The string does not contain doubles.")
}
//END