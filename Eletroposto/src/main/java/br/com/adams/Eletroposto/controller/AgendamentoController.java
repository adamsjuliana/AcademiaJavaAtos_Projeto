package br.com.adams.Eletroposto.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.adams.Eletroposto.model.Agendamento;
import br.com.adams.Eletroposto.model.Usuario;
import br.com.adams.Eletroposto.repository.AgendamentoRepository;
import br.com.adams.Eletroposto.repository.UsuarioRepository;


@Controller
@RequestMapping("/agendamento")
public class AgendamentoController {

	@Autowired
	private AgendamentoRepository agendamentoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;


	@GetMapping("/meus") /* USUARIO VÊ SEUS AGENDAMENTOS */
	public String userAllAgendamentos(Model model, Principal principal) {
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
	now = now.truncatedTo(ChronoUnit.HOURS);
	String nowstr = now.format(dateTimeFormatter);
	List<Agendamento> listaAgendamento = agendamentoRepository.findAgendamentoByUsuarioUSER(principal.getName(), nowstr);
    model.addAttribute("agendamento", listaAgendamento);
    return "meusAgendamentos";
	}	

	@GetMapping("/update/{id}") /* USUARIO AGENDA */
	public String updateAgendamentos(@PathVariable("id") Long id, Model model, Principal principal, RedirectAttributes attributes) {
	//System.out.println(id);
	//System.out.println(principal.getName());
	agendamentoRepository.findAgendamentoAgendamento(principal.getName(), id);
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
	now = now.truncatedTo(ChronoUnit.HOURS);
	String nowstr = now.format(dateTimeFormatter);
	List<Agendamento> listaAgendamento = agendamentoRepository.findAgendamentoByUsuarioUSER(principal.getName(), nowstr);
	model.addAttribute("agendamento", listaAgendamento);
	attributes.addFlashAttribute("mensagem", "Agendamento realizado com sucesso!!");
	return "redirect:/agendamento/meus";
	}

	@GetMapping("/del/{id}") /* DELETAR AGENDAMENTOS */
	public String delAgendamentos(@PathVariable("id") Long id, RedirectAttributes attributes) {
	agendamentoRepository.delAgendamento(null ,id);
	attributes.addFlashAttribute("mensagem", "Agendamento deletado!!");
	return "redirect:/agendamento/meus";
	}
}
