package digraph;

import digraph.IVertex;
import digraph.DiGraph;
import digraph.IEdge;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DiGraphTest {
    
    private DiGraph<String, String> digraph;
    private IVertex<String> vA, vB, vC, vD, vE;
    private IEdge<String, String> ea, eb, ec, ed, ee, ef, eg;
    
    @Before
    public void setUp() {
        digraph = new DiGraph<>();
        
        // Add vertices
        vA = digraph.insertVertex("A");
        vB = digraph.insertVertex("B");
        vC = digraph.insertVertex("C");
        vD = digraph.insertVertex("D");
        vE = digraph.insertVertex("E");
        
        // Add edges
        ea = digraph.insertEdge(vA, vB, "a");
        eb = digraph.insertEdge("A", "D", "b");
        ec = digraph.insertEdge("A", "C", "c");
        ed = digraph.insertEdge("B", "D", "d");
        ee = digraph.insertEdge("D", "E", "e");
        ef = digraph.insertEdge("E", "C", "f");
        eg = digraph.insertEdge("C", "A", "g");
    }

    /**
     * Test of numVertices method, of class DiGraph.
     */
    @Test
    public void testNumVertices() {
        assertEquals("Number of vertices is not correct", 5, digraph.numVertices());
        IVertex<String> vF = digraph.insertVertex("F");
        assertEquals("Number of vertices is not correct", 6, digraph.numVertices());
        IVertex<String> vG = digraph.insertVertex("G");
        assertEquals("Number of vertices is not correct", 7, digraph.numVertices());
        digraph.removeVertex(vG);
        assertEquals("Number of vertices is not correct", 6, digraph.numVertices());
        digraph.removeVertex(vF);
        assertEquals("Number of vertices is not correct", 5, digraph.numVertices());
    }

    /**
     * Test of numEdges method, of class DiGraph.
     */
    @Test
    public void testNumEdges() {
        assertEquals("Number of edges is not correct", 7, digraph.numEdges());
        IEdge<String, String> eh = digraph.insertEdge(vB, vA, "h");
        assertEquals("Number of edges is not correct", 8, digraph.numEdges());
        IEdge<String, String> ei = digraph.insertEdge(vD, vA, "i");
        assertEquals("Number of edges is not correct", 9, digraph.numEdges());
        digraph.removeEdge(ei);
        assertEquals("Number of edges is not correct", 8, digraph.numEdges());
        digraph.removeEdge(eh);
        assertEquals("Number of edges is not correct", 7, digraph.numEdges());
    }

    /**
     * Test of vertices method, of class DiGraph.
     * It also tests insertVertex method
     */
    @Test
    public void testVertices() {
        List<IVertex<String>> listVertices = new ArrayList<>();
        listVertices.add(vA);
        listVertices.add(vB);
        listVertices.add(vC);
        listVertices.add(vD);
        listVertices.add(vE);
        
        Iterable<IVertex<String>> expResult = listVertices;
        Iterable<IVertex<String>> result = digraph.vertices();
        assertEquals("Returned vertices list is not the same", expResult, result);
    }

    /**
     * Test of edges method, of class DiGraph.
     * * It also tests insertEdges method
     */
    @Test
    public void testEdges() {
        List<IEdge<String, String>> listEdges = new ArrayList<>();
        listEdges.add(ea);
        listEdges.add(eb);
        listEdges.add(ec);
        listEdges.add(ed);
        listEdges.add(ee);
        listEdges.add(ef);
        listEdges.add(eg);
        
        Iterable<IEdge<String, String>> expResult = listEdges;
        Iterable<IEdge<String, String>> result = digraph.edges();
        assertEquals("Returned edges list is not the same", expResult, result);
    }

    /**
     * Test of accedentEdges method, of class DiGraph.
     */
    @Test
    public void testAccedentEdges() {
        List<IEdge<String, String>> listEdges = new ArrayList<>();
        Iterable<IEdge<String, String>> expResult;
        Iterable<IEdge<String, String>> result;
        
        // Test vA
        listEdges.clear();
        listEdges.add(ea);
        listEdges.add(eb);
        listEdges.add(ec);
        
        expResult = listEdges;
        result = digraph.accedentEdges(vA);
        assertEquals("Accedent edges from vertex A are not correct", expResult, result);
        
        // Test vB
        listEdges.clear();
        listEdges.add(ed);
        
        expResult = listEdges;
        result = digraph.accedentEdges(vB);
        assertEquals("Accedent edges from vertex B are not correct", expResult, result);
        
        // Test vC
        listEdges.clear();
        listEdges.add(eg);
        
        expResult = listEdges;
        result = digraph.accedentEdges(vC);
        assertEquals("Accedent edges from vertex C are not correct", expResult, result);
        
        // Test vD
        listEdges.clear();
        listEdges.add(ee);
        
        expResult = listEdges;
        result = digraph.accedentEdges(vD);
        assertEquals("Accedent edges from vertex D are not correct", expResult, result);
        
        // Test vE
        listEdges.clear();
        listEdges.add(ef);
        
        expResult = listEdges;
        result = digraph.accedentEdges(vE);
        assertEquals("Accedent edges from vertex E are not correct", expResult, result);
    }

    /**
     * Test of incidentEdges method, of class DiGraph.
     */
    @Test
    public void testIncidentEdges() {
        List<IEdge<String, String>> listEdges = new ArrayList<>();
        Iterable<IEdge<String, String>> expResult;
        Iterable<IEdge<String, String>> result;
        
        // Test vA
        listEdges.clear();
        listEdges.add(eg);
        
        expResult = listEdges;
        result = digraph.incidentEdges(vA);
        assertEquals("Incident edges of vertex A are not correct", expResult, result);
        
        // Test vB
        listEdges.clear();
        listEdges.add(ea);
        
        expResult = listEdges;
        result = digraph.incidentEdges(vB);
        assertEquals("Incident edges of vertex B are not correct", expResult, result);
        
        // Test vC
        listEdges.clear();
        listEdges.add(ec);
        listEdges.add(ef);
        
        expResult = listEdges;
        result = digraph.incidentEdges(vC);
        assertEquals("Incident edges of vertex C are not correct", expResult, result);
        
        // Test vD
        listEdges.clear();
        listEdges.add(eb);
        listEdges.add(ed);
        
        expResult = listEdges;
        result = digraph.incidentEdges(vD);
        assertEquals("Incident edges of vertex D are not correct", expResult, result);
        
        // Test vE
        listEdges.clear();
        listEdges.add(ee);
        
        expResult = listEdges;
        result = digraph.incidentEdges(vE);
        assertEquals("Incident edges of vertex E are not correct", expResult, result);
    }

    /**
     * Test of opposite method, of class DiGraph.
     */
    @Test
    public void testOpposite() {
        assertEquals("Opposite vertex is not correct", digraph.opposite(vA, ea), vB);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vA, eb), vD);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vA, ec), vC);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vA, ed), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vA, ee), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vA, ef), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vA, eg), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vB, ea), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vB, eb), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vB, ec), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vB, ed), vD);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vB, ee), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vB, ef), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vB, eg), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vC, ea), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vC, eb), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vC, ec), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vC, ed), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vC, ef), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vC, eg), vA);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vD, ea), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vD, eb), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vD, ec), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vD, ed), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vD, ee), vE);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vD, ef), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vD, eg), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vE, ea), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vE, eb), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vE, ec), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vE, ed), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vE, ee), null);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vE, ef), vC);
        assertEquals("Opposite vertex is not correct", digraph.opposite(vE, eg), null);
    }

    /**
     * Test of areAdjacent method, of class DiGraph.
     */
    @Test
    public void testAreAdjacent() {
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vA, vA), false);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vA, vB), true);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vA, vC), true);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vA, vD), true);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vA, vE), false);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vB, vA), false);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vB, vB), false);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vB, vC), false);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vB, vD), true);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vB, vE), false);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vC, vA), true);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vC, vB), false);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vC, vC), false);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vC, vD), false);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vC, vE), false);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vD, vA), false);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vD, vB), false);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vD, vC), false);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vD, vD), false);
        assertEquals("Are adjacent vertex is not correct", digraph.areAdjacent(vD, vE), true);
    }
    
    /**
     * Test of removeVertex method, of class DiGraph.
     */
    @Test
    public void testRemoveVertex() {
        String removedVertex = digraph.removeVertex(vD);
        
        List<IVertex<String>> listVertices = new ArrayList<>();
        listVertices.add(vA);
        listVertices.add(vB);
        listVertices.add(vC);
        listVertices.add(vE);
        
        Iterable<IVertex<String>> expVertices = listVertices;
        Iterable<IVertex<String>> graphVertices = digraph.vertices();
        
        assertEquals("The vertex has not been removed", expVertices, graphVertices);
        assertEquals("Removed vertex is not the specfied one", removedVertex, "D");
    }

    /**
     * Test of removeEdge method, of class DiGraph.
     */
    @Test
    public void testRemoveEdge() {
        String removedEdge = digraph.removeEdge(ed);
        
        List<IEdge<String, String>> listEdges = new ArrayList<>();
        listEdges.add(ea);
        listEdges.add(eb);
        listEdges.add(ec);
        listEdges.add(ee);
        listEdges.add(ef);
        listEdges.add(eg);
        
        Iterable<IEdge<String, String>> expEdges = listEdges;
        Iterable<IEdge<String, String>> graphEdges = digraph.edges();
        
        assertEquals("The edge has not been removed", expEdges, graphEdges);
        assertEquals("Removed edge is not the specfied one", removedEdge, "d");
    }

    /**
     * Test of replace method, of class DiGraph.
     */
    @Test
    public void testReplace_Vertex() {
        String oldVertex = digraph.replace(vA, "New");
        
        assertEquals("Vertex element has not been replaced", vA.element(), "New");
        assertEquals("Old element of replaced vertex is not correct", oldVertex, "A");
    }

    /**
     * Test of replace method, of class DiGraph.
     */
    @Test
    public void testReplace_Edge() {
        String oldEdge = digraph.replace(ea, "new");
        
        assertEquals("Edge element has not been replaced", ea.element(), "new");
        assertEquals("Old element of replaced edge is not correct", oldEdge, "a");
    }
    
}
