# SE-Experiment-1
Implement a translator which generates a direct graph with a text file.

*This README plays the role of a project documentation.*

## Architecture

1. Main class (main.java)
    1. It only contains the **logic** of the entire program process.
    2. The valid parameter(s) of the program:
        * **FILENAME** Run the program normally.
        * **-h** Print the help text.
        * **NONE** Print the help text.
    3. The main function call the `void run(String[] args)` to perform the main process.
       The purpose of this design is to extraced some code out of the `try` block.
2. A simple DirectedGraph class (DirectedGraph.java)

    The methods of this class are:
    ```java
    class DirectedGraph {
        // Test whether there is an arc from v1 to v2.
        boolean isArc(int, int);
        
        // Add an arc from v1 to v2. Return false if there is already an arc.
        boolean addArc(int, int, Integer);
        
        // Test whether the vertex is colored.
        boolean isColored(int);
        
        // Color a vertex. Return false if it is already colored.
        boolean colorVertex(int); 
        
        // Wipe all colors on vertices.
        void cleanColors(); 
    }
    ```

3. Words graph class (WordsGraph.java)
    1. Data member:
    
        <code> DirectedGraph<String, Integer> aGraph;  </code>
        The graph class is from [this project](https://github.com/jgrapht/jgrapht).
        
    2. Methods:
        1. [**Constructor**]Read in a file and translate it into a direct graph.
        
            **Interface:**
            
            |     Name    | Parameter Type | Return Value Type |
            |:-----------:|:--------------:|:-----------------:|
            |  WordsGraph |     String     |         -         |

        2. Show the graph and save it as a new picture file.
         
            **Interface:**
            
            |        Name       | Parameter Type | Return Value Type |
            |:-----------------:|:--------------:|:-----------------:|
            | showDirectedGraph |        -       |        void       |
         
        3. Get all **bridge words** from word1 to word2.
         
            **Interface:**
            
            |       Name       | Parameter Type | Return Value Type |
            |:----------------:|:--------------:|:-----------------:|
            | queryBridgeWords | String, String |      String[]     |

        4. Generate new text in terms of the newly input text and the graph.
         
            **Requirements:**
            
            1. If two words in the newly input text have *ONE* bridge word in the graph. Insert it into the text.
            2. If two words in the newly input text have *MORE THAN ONE* bridge word in the graph. Insert one randomly.
            3. If two words in the newly input text have no bridge word in the graph. Insert nothing.

            **Interface:**
            
            |       Name      | Parameter Type | Return Value Type |
            |:---------------:|:--------------:|:-----------------:|
            | generateNewText |     String     |       String      |
            
        5. Get the shortest path(s) from word1 to word2 in the graph.
         
            **Requirements:**
            
            1. If there are several shortest paths between them, return them all.
            2. If there is no shortest path between them, return an empty array.
            3. The shortest path(s) should be showed in the form like "to -> explore -> band".
            
            **Interface:**
            
            |       Name       | Parameter Type | Return Value Type |
            |:----------------:|:--------------:|:-----------------:|
            | calcShortestPath | String, String |      String[]     |
            
        6. Get all shortest paths from one word to other words. (Requirements are the same to the above.)
         
            **Interface:**
            
            |       Name       | Parameter Type | Return Value Type |
            |:----------------:|:--------------:|:-----------------:|
            | calcShortestPath |     String     |      String[]     |
            
        7. Traverse the graph from a random words as a start point.
         
            **Requirements:**
            
            1. The traversal path is random.
            2. Only outarcs can be visited during the traversal.
            3. The traversal stops whenever:
                1. An arc is visited twice.
                2. There is no next reachable word.
            4. The traversal path should be showed in the form like "to -> explore -> band".
            
            **Interface:**
            
            |    Name    | Parameter Type | Return Value Type |
            |:----------:|:--------------:|:-----------------:|
            | randomWalk |        -       |       String      |
            
## Collaborators
[@ANDI_Mckee](https://github.com/ANDI-Mckee)

[@fake0fan](https://github.com/fake0fan)
