package biologicalparkticketsystem.model;

import biologicalparkticketsystem.controller.CourseManager;
import java.nio.file.FileSystems;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CourseManagerTest {
    
    @Before
    public void setUp() {
    }

    /**
     * Test of loadParkMap method, of class CourseManager.
     */
    @Test
    public void testLoadParkMap() {
        String filePath = FileSystems.getDefault().getPath("mapa1.dat").toAbsolutePath().toString();
        CourseManager instance = new CourseManager();
        instance.loadCourseMapFile(filePath);
        
        String expResult = "COURSE PLANER (8 Points of Interest | 12 Connections)\n" +
"	Moinho(5) TO Quinta Pedagogica(7)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Moinho(5) TO Entrada(1)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Moinho(5) TO Papagaios(8)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Moinho(5) TO Tigres(2)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Moinho(5) TO Pomar(3)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Moinho(5) TO Veados(4)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Moinho(5) TO Lago(6)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Quinta Pedagogica(7) TO Moinho(5)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Quinta Pedagogica(7) TO Entrada(1)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Quinta Pedagogica(7) TO Papagaios(8)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Quinta Pedagogica(7) TO Tigres(2)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Quinta Pedagogica(7) TO Pomar(3)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Quinta Pedagogica(7) TO Veados(4)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Quinta Pedagogica(7) TO Lago(6)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Entrada(1) TO Moinho(5)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Entrada(1) TO Quinta Pedagogica(7)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Entrada(1) TO Papagaios(8)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Entrada(1) TO Tigres(2)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Entrada(1) TO Pomar(3)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Entrada(1) TO Veados(4)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Entrada(1) TO Lago(6)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Papagaios(8) TO Moinho(5)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Papagaios(8) TO Quinta Pedagogica(7)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Papagaios(8) TO Entrada(1)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Papagaios(8) TO Tigres(2)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Papagaios(8) TO Pomar(3)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Papagaios(8) TO Veados(4)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Papagaios(8) TO Lago(6)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Tigres(2) TO Moinho(5)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Tigres(2) TO Quinta Pedagogica(7)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Tigres(2) TO Entrada(1)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Tigres(2) TO Papagaios(8)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Tigres(2) TO Pomar(3)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Tigres(2) TO Veados(4)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Tigres(2) TO Lago(6)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Pomar(3) TO Moinho(5)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Pomar(3) TO Quinta Pedagogica(7)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Pomar(3) TO Entrada(1)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Pomar(3) TO Papagaios(8)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Pomar(3) TO Tigres(2)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Pomar(3) TO Veados(4)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Pomar(3) TO Lago(6)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Veados(4) TO Moinho(5)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Veados(4) TO Quinta Pedagogica(7)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Veados(4) TO Entrada(1)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Veados(4) TO Papagaios(8)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Veados(4) TO Tigres(2)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Veados(4) TO Pomar(3)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Veados(4) TO Lago(6)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Lago(6) TO Moinho(5)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Lago(6) TO Quinta Pedagogica(7)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Lago(6) TO Entrada(1)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Lago(6) TO Papagaios(8)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Lago(6) TO Tigres(2)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Lago(6) TO Pomar(3)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n" +
"	Lago(6) TO Veados(4)\n" +
"		ponteVermelha {type bridge, 50 meters, 10 €}\n" +
"		alameda {type path, 100 meters, 1 €}\n" +
"		rosas {type path, 180 meters, 1 €}\n" +
"		silvestre {type path, 350 meters, 20 €}\n" +
"		ruidoso {type path, 50 meters, 12 €}\n" +
"		quinta {type path, 250 meters, 8 €}\n" +
"		ponteLago {type bridge, 20 meters, 30 €}\n" +
"		floresta {type path, 150 meters, 0 €}\n" +
"		florido {type path, 20 meters, 15 €}\n" +
"		ponteLilas {type bridge, 50 meters, 10 €}\n" +
"		selva {type path, 200 meters, 2 €}\n" +
"		escuro {type path, 300 meters, 2 €}\n" +
"\n";
        
        assertEquals("Park map file has not been correctly loaded", instance.toString(), expResult);
    }

    /**
     * Test of addPointOfInterest method, of class CourseManager.
     */
    @Test
    public void testAddPointOfInterest() {
        CourseManager instance = new CourseManager();
        PointOfInterest poi1 = new PointOfInterest(1, "POI1");
        PointOfInterest poi2 = new PointOfInterest(2, "POI2");
        instance.addPointOfInterest(poi1);
        instance.addPointOfInterest(poi2);
        
        String expResult = "COURSE PLANER (2 Points of Interest | 0 Connections)\n" +
"	POI2(2) TO POI1(1)\n" +
"		(no connections)\n" +
"\n" +
"	POI1(1) TO POI2(2)\n" +
"		(no connections)\n" +
"\n";
        
        assertEquals("Points of interest has not been correctly added", instance.toString(), expResult);
    }

    /**
     * Test of addConnection method, of class CourseManager.
     */
    @Test
    public void testAddConnection() {
        CourseManager instance = new CourseManager();
        PointOfInterest poi1 = new PointOfInterest(1, "POI1");
        PointOfInterest poi2 = new PointOfInterest(2, "POI2");
        instance.addPointOfInterest(poi1);
        instance.addPointOfInterest(poi2);
        
        Connection con = new ConnectionBridge(1, "CON1", 10, 100, true);
        instance.addConnection(poi1, poi2, con);
        
        String expResult = "COURSE PLANER (2 Points of Interest | 1 Connections)\n" +
"	POI1(1) TO POI2(2)\n" +
"		CON1 {type bridge, 100 meters, 10 €}\n" +
"\n" +
"	POI2(2) TO POI1(1)\n" +
"		CON1 {type bridge, 100 meters, 10 €}\n" +
"\n";
        
        assertEquals("Connections has not been correctly added", instance.toString(), expResult);
    }
    
//    /**
//     * Test of getConnectionsBetween method, of class CourseManager.
//     */
//    @Test
//    public void testGetConnectionsBetween() {
//        CourseManager instance = new CourseManager();
//        PointOfInterest poi1 = new PointOfInterest(1, "POI1");
//        PointOfInterest poi2 = new PointOfInterest(2, "POI2");
//        PointOfInterest poi3 = new PointOfInterest(3, "POI3");
//        PointOfInterest poi4 = new PointOfInterest(4, "POI4");
//        instance.addPointOfInterest(poi1);
//        instance.addPointOfInterest(poi2);
//        instance.addPointOfInterest(poi3);
//        instance.addPointOfInterest(poi4);
//        
//        Connection con1 = new ConnectionBridge(1, "CON1", 10, 100, true);
//        Connection con2 = new ConnectionBridge(2, "CON2", 20, 200, true);
//        Connection con3 = new ConnectionBridge(3, "CON3", 30, 300, true);
//        instance.addConnection(poi1, poi2, con1);
//        instance.addConnection(poi3, poi4, con2);
//        instance.addConnection(poi2, poi3, con3);
//        
//        List<Connection> t = instance.getConnectionsBetween(poi1, poi2);
//        
//        //assertEquals("Connections has not been correctly added", instance.toString(), expResult);
//    }
    
}
