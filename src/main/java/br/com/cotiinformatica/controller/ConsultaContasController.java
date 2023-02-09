package br.com.cotiinformatica.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.cotiinformatica.dtos.UsuarioDTO;
import br.com.cotiinformatica.entities.Conta;
import br.com.cotiinformatica.models.ConsultaContasModel;
import br.com.cotiinformatica.repositories.ContaRepository;

@Controller
	public class ConsultaContasController {
		
		@Autowired
		private ContaRepository contaRepository;

		@RequestMapping(value = "/admin/consulta-contas")
		public ModelAndView consultaContas() {
				
			ModelAndView modelAndView = new ModelAndView("admin/consulta-contas");
			modelAndView.addObject("model", new ConsultaContasModel());
		
			return modelAndView;
	}
	@RequestMapping(value = "/admin/consultar-contas", method= RequestMethod.POST)	
	public ModelAndView consultarContas(ConsultaContasModel model, HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("admin/consulta-contas");
		
		try {
			UsuarioDTO usuarioDto = (UsuarioDTO) request.getSession().getAttribute("usuario");
			Date dataIni = new SimpleDateFormat("yyyy-MM-dd").parse(model.getDataIni());
			Date dataFim = new SimpleDateFormat("yyyy-MM-dd").parse(model.getDataFim());
			List<Conta> contas = contaRepository.findByUsuarioAndData(usuarioDto.getIdUsuario(), dataIni, dataFim);
			modelAndView.addObject("contas", contas);
			
		}catch(Exception e) {
			modelAndView.addObject("mensagem", "Falha ao consultar contas: " + e.getMessage());
		}
		
		modelAndView.addObject("model", model);
		return modelAndView;
	}
	
}
