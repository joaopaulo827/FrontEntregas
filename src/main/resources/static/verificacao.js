/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
function validarCadastro(){
    const nome = document.getElementById("nome");
    const email = document.getElementById("email");
    const senha = document.getElementById("senha");
    const confirma= document.getElementById("confirmar");
    const botao = document.getElementById("botao");
    
    if(nome.value.length>0 && email.value.length>0 && senha.value.length>0 && confirma.value.length>0){
        botao.disabled=false;
    }
}
