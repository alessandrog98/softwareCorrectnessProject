//BEGIN
package main

import (
    "fmt"
    "strings"
)

func main() {
    var s string
    fmt.Println("Enter a string (max length 50):")
    fmt.Scanln(&s)
    if len(s) > 50 {
        fmt.Println("String length exceeds 50.")
        return
    }
    s = strings.ToLower(s)
    fmt.Println("Transformed string:", s)
    for i := 0; i < len(s)-1; i++ {
        if s[i] == s[i+1] {
            fmt.Println("The string contains doubles.")
            return
        }
    }
    fmt.Println("The string does not contain doubles.")
}
//END