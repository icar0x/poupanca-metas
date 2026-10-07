package com.icaro.poupanca_metas.cli;

import com.icaro.poupanca_metas.entity.MetaProgresso;
import com.icaro.poupanca_metas.entity.Usuario;
import com.icaro.poupanca_metas.repository.UsuarioRepository;
import com.icaro.poupanca_metas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class MenuCLI implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MetaService metaService;
    @Autowired
    private DepositoService depositoService;
    @Autowired
    private JurosService jurosService;
    @Autowired
    private RelatorioService relatorioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            int opcao = lerInteiro("Escolha uma opção: ");
            try {
                switch (opcao) {
                    case 1 -> criarUsuario();
                    case 2 -> criarMeta();
                    case 3 -> listarMetas();
                    case 4 -> fazerDeposito();
                    case 5 -> verProgresso();
                    case 6 -> calcularJuros();
                    case 7 -> concluirMeta();
                    case 8 -> relatorioMensal();
                    case 0 -> rodando = false;
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
            System.out.println();
        }
        System.out.println("Encerrando o sistema. Até logo!");
    }

    private void exibirMenu() {
        System.out.println("=== SISTEMA DE POUPANÇA COM METAS ===");
        System.out.println("1. Cadastrar usuário");
        System.out.println("2. Criar nova meta");
        System.out.println("3. Listar minhas metas");
        System.out.println("4. Fazer depósito em meta");
        System.out.println("5. Ver progresso de uma meta");
        System.out.println("6. Calcular e aplicar juros");
        System.out.println("7. Marcar meta como concluída");
        System.out.println("8. Relatório financeiro mensal");
        System.out.println("0. Sair");
    }

    private void criarUsuario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        Usuario usuario = usuarioService.cadastrar(nome, email);
        System.out.println("Usuário criado com id " + usuario.getId());
    }

    private void criarMeta() {
        Usuario usuario = buscarUsuario();
        System.out.print("Nome da meta: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        BigDecimal valorAlvo = lerBigDecimal("Valor alvo: ");
        System.out.print("Data meta (AAAA-MM-DD): ");
        LocalDate dataMeta = LocalDate.parse(scanner.nextLine());

        metaService.criarMeta(usuario, nome, descricao, valorAlvo, dataMeta);
        System.out.println("Meta criada com sucesso!");
    }

    private void listarMetas() {
        Integer usuarioId = lerInteiro("Id do usuário: ");
        List<MetaProgresso> metas = metaService.listarComProgresso(usuarioId);
        if (metas.isEmpty()) {
            System.out.println("Nenhuma meta encontrada.");
            return;
        }
        for (MetaProgresso m : metas) {
            System.out.printf("[%d] %s | Alvo: R$ %.2f | Atual: R$ %.2f | Progresso: %.2f%% | Dias restantes: %d | Status: %s%n",
                    m.getMetaId(), m.getNomeMeta(), m.getValorAlvo(), m.getValorAtual(),
                    m.getPercentualProgresso(), m.getDiasRestantes(), m.getStatus());
        }
    }

    private void fazerDeposito() {
        Integer metaId = lerInteiro("Id da meta: ");
        BigDecimal valor = lerBigDecimal("Valor a depositar: ");
        depositoService.registrarDeposito(metaId, valor);
        System.out.println("Depósito registrado com sucesso!");
    }

    private void verProgresso() {
        Integer metaId = lerInteiro("Id da meta: ");
        MetaProgresso m = metaService.buscarProgresso(metaId);
        System.out.printf("%s | Alvo: R$ %.2f | Atual: R$ %.2f | Progresso: %.2f%% | Status: %s%n",
                m.getNomeMeta(), m.getValorAlvo(), m.getValorAtual(),
                m.getPercentualProgresso(), m.getStatus());
    }

    private void calcularJuros() {
        Integer metaId = lerInteiro("Id da meta: ");
        BigDecimal taxaAnual = lerBigDecimal("Taxa anual (ex: 0.06 para 6%): ");
        BigDecimal juros = jurosService.calcularEAplicarJuros(metaId, taxaAnual);
        System.out.println("Juros aplicados: R$ " + juros);
    }

    private void concluirMeta() {
        Integer metaId = lerInteiro("Id da meta: ");
        metaService.marcarComoConcluida(metaId);
        System.out.println("Meta marcada como concluída!");
    }

    private void relatorioMensal() {
        Integer usuarioId = lerInteiro("Id do usuário: ");
        RelatorioMensal r = relatorioService.gerarRelatorioMensal(usuarioId);
        System.out.println("RELATÓRIO DO MÊS");
        System.out.println("Total depositado: R$ " + r.totalDepositado());
        System.out.println("Total em juros: R$ " + r.totalJuros());
        System.out.println("Saldo total: R$ " + r.saldoTotal());
        System.out.println("Metas concluídas: " + r.metasConcluidas());
        System.out.println("Metas em progresso: " + r.metasEmProgresso());
    }

    private Usuario buscarUsuario() {
        Integer usuarioId = lerInteiro("Id do usuário: ");
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    private int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        int valor = Integer.parseInt(scanner.nextLine());
        return valor;
    }

    private BigDecimal lerBigDecimal(String mensagem) {
        System.out.print(mensagem);
        return new BigDecimal(scanner.nextLine());
    }
}