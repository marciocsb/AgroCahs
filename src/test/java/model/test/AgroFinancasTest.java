package model.test;

import org.junit.jupiter.api.*;
import model.AgroController;
import model.Usuario;
import model.Lavoura;
import model.Despesa;
import model.TipoDespesa;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes automáticos para validar funcionalidades principais do sistema AgroFinancas.
 */
public class AgroFinancasTest {

    private AgroController controller;
    private Usuario u1;

    @BeforeEach
    public void setup() {
        controller = new AgroController();
        u1 = new Usuario("João", "12345678900");
        controller.adicionarUsuario(u1);
    }

    @Test
    public void testAdicionarUsuario() {
        Usuario u2 = new Usuario("Maria", "98765432100");
        controller.adicionarUsuario(u2);
        assertTrue(controller.getUsuarios().contains(u2));
    }

    @Test
    public void testAdicionarLavoura() throws Exception {
        Lavoura l1 = new Lavoura("Milho", 10);
        controller.adicionarLavoura(u1, l1);

        List<Lavoura> lavouras = controller.getLavouras(u1);
        assertEquals(1, lavouras.size());
        assertEquals("Milho", lavouras.get(0).getNome());
    }

    @Test
    public void testRemoverLavoura() throws Exception {
        controller.adicionarLavoura(u1, new Lavoura("Soja", 15));
        boolean removido = controller.removerLavoura(u1, "Soja");
        assertTrue(removido);
        assertTrue(controller.getLavouras(u1).isEmpty());
    }

    @Test
    public void testRegistrarDespesaECalculoTotal() throws Exception {
        Lavoura l = new Lavoura("Arroz", 20);
        controller.adicionarLavoura(u1, l);
        controller.registrarDespesa(u1, "Arroz", new Despesa(TipoDespesa.MAO_DE_OBRA, 500));
        controller.registrarDespesa(u1, "Arroz", new Despesa(TipoDespesa.FERTILIZANTES, 250));

        Lavoura lavoura = controller.pesquisarLavoura(u1, "Arroz");
        double total = lavoura.calcularTotal();
        assertEquals(750, total);
    }

    @Test
    public void testPesquisarLavouraComStream() throws Exception {
        controller.adicionarLavoura(u1, new Lavoura("Feijão", 8));
        Lavoura resultado = controller.getLavouras(u1).stream().filter(l -> l.getNome().startsWith("F")).findFirst().orElse(null);

        assertNotNull(resultado);
        assertEquals("Feijão", resultado.getNome());
    }
    @Test
    public void testRemoverUsuario() {
        controller.removerUsuario(u1);
        assertFalse(controller.getUsuarios().contains(u1));
    }
    @Test
    public void testDespesasTotaisUsuario() throws Exception {
        Lavoura l1 = new Lavoura("Milho", 10);
        Lavoura l2 = new Lavoura("Soja", 15);
        controller.adicionarLavoura(u1, l1);
        controller.adicionarLavoura(u1, l2);

        controller.registrarDespesa(u1, "Milho", new Despesa(TipoDespesa.FERTILIZANTES, 200));
        controller.registrarDespesa(u1, "Soja", new Despesa(TipoDespesa.MAO_DE_OBRA, 300));

        double total = controller.getLavouras(u1).stream()
                .flatMap(l -> l.getDespesas().stream())
                .mapToDouble(Despesa::getValor)
                .sum();

        assertEquals(500, total);
    }

}