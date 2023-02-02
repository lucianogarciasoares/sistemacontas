package br.com.cotiinformatica.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.cotiinformatica.dtos.UsuarioDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.models.AutenticarModel;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Controller
public class AutenticarController {
	@Autowired
	private UsuarioRepository usuarioRepository;

	@RequestMapping(value = "/")
	public ModelAndView autenticar() {
		ModelAndView modelAndView = new ModelAndView("autenticar");
		modelAndView.addObject("model", new AutenticarModel());

		return modelAndView;
	}

	@RequestMapping(value = "/autenticar-usuario", method = RequestMethod.POST)
	public ModelAndView autenticarUsuario(AutenticarModel model, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("autenticar");

		modelAndView.addObject("model", model);

		try {
			Usuario usuario = usuarioRepository.findByEmailAndSenha(model.getEmail(), model.getSenha());
			if (usuario != null) {
				UsuarioDTO usuarioDTO = new UsuarioDTO();

				usuarioDTO.setIdUsuario(usuario.getIdUsuario());
				usuarioDTO.setNome(usuario.getNome());
				usuarioDTO.setEmail(usuario.getEmail());
				usuarioDTO.setDataHoraAcesso(new Date());

				request.getSession().setAttribute("usuario", usuarioDTO);

				modelAndView.setViewName("redirect:/admin/dashboard");

			} else {
				modelAndView.addObject("mensagem_erro", "Acesso negado. Usuário não encontrado");
			}
		} catch (Exception e) {
			modelAndView.addObject("mensagem_erro", e.getMessage());
		}
		return modelAndView;
	}
}
