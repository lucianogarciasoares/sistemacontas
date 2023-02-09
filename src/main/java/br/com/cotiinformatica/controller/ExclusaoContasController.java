package br.com.cotiinformatica.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.cotiinformatica.dtos.UsuarioDTO;
import br.com.cotiinformatica.entities.Conta;
import br.com.cotiinformatica.models.ConsultaContasModel;
import br.com.cotiinformatica.repositories.ContaRepository;

@Controller
public class ExclusaoContasController {

	@Autowired
	private ContaRepository contaRepository;

	@RequestMapping(value = "/admin/exclusao-contas")
	public ModelAndView exclusaoContas(Integer id, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("admin/consulta-contas");

		try {

			UsuarioDTO usuarioDto = (UsuarioDTO) request.getSession().getAttribute("usuario");

			Conta conta = contaRepository.findById(id, usuarioDto.getIdUsuario());

			if (conta != null) {

				contaRepository.delete(conta);

				modelAndView.addObject("mensagem", "Conta '" + conta.getNome() + "' excluída com sucesso.");
			}
		} catch (Exception e) {
			modelAndView.addObject("mensagem", "Falha ao excluir conta: " + e.getMessage());
		}

		modelAndView.addObject("model", new ConsultaContasModel());
		return modelAndView;
	}

}
