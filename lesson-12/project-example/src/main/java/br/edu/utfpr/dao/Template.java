/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.dao;

import br.edu.utfpr.dto.PaisDTO;
import java.util.List;

/**
 *
 * @author isabelle
 */
public abstract class Template<T> {
    protected abstract boolean incluir(PaisDTO pais);
    protected abstract List<T> listarTodos();
    protected abstract boolean excluir(int id);
    protected abstract boolean alterar(PaisDTO pais);
    protected abstract PaisDTO listarPorId (int id);
    
    public final void template(){
        int id = 0;
        PaisDTO pais = new PaisDTO();
        
        this.incluir(pais);
        this.listarTodos();
        this.excluir(id);
        this.alterar(pais);
        this.listarPorId(id);
    }
}
