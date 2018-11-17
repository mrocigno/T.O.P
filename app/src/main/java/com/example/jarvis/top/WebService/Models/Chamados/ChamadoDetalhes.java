package com.example.jarvis.top.WebService.Models.Chamados;

public class ChamadoDetalhes {

    int status;
    String mensagem;
    ResultadoDetalhes resultado;

    public ChamadoDetalhes(int status, String mensagem, ResultadoDetalhes resultado) {
        this.status = status;
        this.mensagem = mensagem;
        this.resultado = resultado;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public ResultadoDetalhes getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoDetalhes resultado) {
        this.resultado = resultado;
    }

    public class ResultadoDetalhes{
        String cliente;
        String telefone;
        String celular;
        String Titulo;
        String numero;
        String latitude;
        String longitude;
        String Prioriedade;
        String Empresa;
        String Setor;
        String Solicitante;
        String Responsavel;
        String descricao;
        String cep;
        String logradouro;
        String bairro;
        String cidade;
        String estado;
        String tem_comentario;
        String id_status;
        String status;
        String id_enviado_para;
        String id_endereco;

        public ResultadoDetalhes(String cliente, String telefone, String celular, String titulo, String numero, String latitude, String longitude, String prioriedade, String empresa, String setor, String solicitante, String responsavel, String descricao, String cep, String logradouro, String bairro, String cidade, String estado, String tem_comentario, String id_status, String status, String id_enviado_para, String id_endereco) {
            this.cliente = cliente;
            this.telefone = telefone;
            this.celular = celular;
            Titulo = titulo;
            this.numero = numero;
            this.latitude = latitude;
            this.longitude = longitude;
            Prioriedade = prioriedade;
            Empresa = empresa;
            Setor = setor;
            Solicitante = solicitante;
            Responsavel = responsavel;
            this.descricao = descricao;
            this.cep = cep;
            this.logradouro = logradouro;
            this.bairro = bairro;
            this.cidade = cidade;
            this.estado = estado;
            this.tem_comentario = tem_comentario;
            this.id_status = id_status;
            this.status = status;
            this.id_enviado_para = id_enviado_para;
            this.id_endereco = id_endereco;
        }

        public String getCliente() {
            return cliente;
        }

        public void setCliente(String cliente) {
            this.cliente = cliente;
        }

        public String getTelefone() {
            return telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        public String getCelular() {
            return celular;
        }

        public void setCelular(String celular) {
            this.celular = celular;
        }

        public String getTitulo() {
            return Titulo;
        }

        public void setTitulo(String titulo) {
            Titulo = titulo;
        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getPrioriedade() {
            return Prioriedade;
        }

        public void setPrioriedade(String prioriedade) {
            Prioriedade = prioriedade;
        }

        public String getEmpresa() {
            return Empresa;
        }

        public void setEmpresa(String empresa) {
            Empresa = empresa;
        }

        public String getSetor() {
            return Setor;
        }

        public void setSetor(String setor) {
            Setor = setor;
        }

        public String getSolicitante() {
            return Solicitante;
        }

        public void setSolicitante(String solicitante) {
            Solicitante = solicitante;
        }

        public String getResponsavel() {
            return Responsavel;
        }

        public void setResponsavel(String responsavel) {
            Responsavel = responsavel;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public String getCep() {
            return cep;
        }

        public void setCep(String cep) {
            this.cep = cep;
        }

        public String getLogradouro() {
            return logradouro;
        }

        public void setLogradouro(String logradouro) {
            this.logradouro = logradouro;
        }

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public String getCidade() {
            return cidade;
        }

        public void setCidade(String cidade) {
            this.cidade = cidade;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public String getTem_comentario() {
            return tem_comentario;
        }

        public void setTem_comentario(String tem_comentario) {
            this.tem_comentario = tem_comentario;
        }

        public String getId_status() {
            return id_status;
        }

        public void setId_status(String id_status) {
            this.id_status = id_status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getId_enviado_para() {
            return id_enviado_para;
        }

        public void setId_enviado_para(String id_enviado_para) {
            this.id_enviado_para = id_enviado_para;
        }

        public String getId_endereco() {
            return id_endereco;
        }

        public void setId_endereco(String id_endereco) {
            this.id_endereco = id_endereco;
        }
    }
}
