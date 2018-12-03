package br.edu.ulbra.election.voter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	//GET / - Página inicial do VOTER. Aqui se faz cadastro.
	@RequestMapping("/login")
    public String home(){
        return "<body style=\"background: #e4e4e4; width: 1000px; margin: 0 auto; font-family: 'Open Sans';\">\r\n" + 
        		"	\r\n" + 
        		"	<div style=\"width: 200px; margin: 0 auto; margin-top: 100px; text-align: center;font-weight: 600;\">\r\n" + 
        		"		LOGIN\r\n" + 
        		"	</div>\r\n" + 
        		"	<div style=\"background: #fff; padding: 30px; width: 200px; margin: 0 auto; margin-top: 10px; border-radius: 10px;	padding-top: 50px;box-shadow: 0 8px 36px -6px black;\">\r\n" + 
        		"	\r\n" + 
        		"		\r\n" + 
        		"		<form action=\"\" method=\"\">\r\n" + 
        		"		\r\n" + 
        		"			<div>\r\n" + 
        		"				<input placeholder=\"Seu nome\" name=\"\" id=\"\" type=\"text\" style=\"width: 100%; border: none; font-size: 18px; border-bottom: 1px solid;\">\r\n" + 
        		"			</div>\r\n" + 
        		"			\r\n" + 
        		"			<div style=\"margin-top: 20px;\">\r\n" + 
        		"\r\n" + 
        		"				<input placeholder=\"Senha\" name=\"\" id=\"\" type=\"password\" style=\"width: 100%; border: none; font-size: 15px; border-bottom: 1px solid;\">\r\n" + 
        		"			</div>\r\n" + 
        		"			<div style=\"width: 100%; text-align: center; margin-top: 10px; padding-top: 20px;\">\r\n" + 
        		"				<a href=\"http://localhost:8084/vote\">\r\n" + 
        		"				<input type=\"button\" Value=\"Entrar\" style=\" width: 100%; background: #4b88dc; color: #fff; border: none; padding: 10px; border-radius: 5px;\">\r\n" + 
        		"				</a>\r\n" + 
        		"			</div>\r\n" + 
        		"		</form>\r\n" + 
        		"\r\n" + 
        		"\r\n" + 
        		"\r\n" + 
        		"	</div>\r\n" + 
        		"</body>\r\n" + 
        		"\r\n" + 
        		""
        		
        		
        		
        		;
    }/*ate aqui ok*/
	
}
