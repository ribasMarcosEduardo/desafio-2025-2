const locacaoState = { filmesSelecionados: [], currentFilme: {} };

const $ = (selector) => document.querySelector(selector);
const $$ = (selector) => document.querySelectorAll(selector);

const toggleSections = (showFilmes) => {
    $("#tabelaFilmesSection").style.display = showFilmes ? "block" : "none";
    $("#tabelaExemplaresSection").style.display = showFilmes ? "none" : "block";
    $("#tituloFilmeSelecionado").textContent = showFilmes ? "" : `Exemplares de: ${locacaoState.currentFilme.titulo}`;
};

async function abrirExemplares(id, titulo) {
    locacaoState.currentFilme = { id, titulo };
    toggleSections(false);

    const tbody = $("#tabelaExemplaresBody");
    tbody.innerHTML = "";

    try {
        const res = await fetch(`/locacao/exemplaresPorTitulo?titulo=${encodeURIComponent(titulo)}`);
        if (!res.ok) {
            throw new Error(`HTTP error! status: ${res.status}`);
        }
        const exemplares = await res.json();

        tbody.innerHTML = exemplares.length
            ? exemplares.map(e => `
                <tr>
                    <td>${e.id}</td>
                    <td>${formatDate(e.dataCadastro)}</td>
                    <td>
                        <button type="button" class="btn btn-success btn-sm" onclick="selecionarExemplar('${e.id}')">Selecionar</button>
                    </td>
                </tr>`).join("")
            : `<tr><td colspan="3" class="text-center">Nenhum exemplar disponível</td></tr>`;
    } catch(error) {
        console.error("Erro ao carregar exemplares:", error);
        tbody.innerHTML = `<tr><td colspan="3" class="text-center text-danger">Erro ao carregar exemplares.</td></tr>`;
    }
}

const formatDate = (d) => {
    if (!d) return "N/A";
    const date = new Date(d);
    if (isNaN(date.getTime())) {
        return "Data inválida";
    }
    return date.toLocaleDateString("pt-BR");
};

function selecionarExemplar(idExemplar) {
    const { titulo } = locacaoState.currentFilme;
    const idExemplarNum = parseInt(idExemplar, 10);

    if (locacaoState.filmesSelecionados.length >= 3) {
        alert("Você só pode selecionar até 3 exemplares.");
        return;
    }

    const jaSelecionado = locacaoState.filmesSelecionados.some(f => parseInt(f.idExemplar, 10) === idExemplarNum);
    if (jaSelecionado) {
        alert("Este exemplar já foi selecionado.");
        return;
    }

    locacaoState.filmesSelecionados.push({ titulo, idExemplar });
    atualizarTabelaFilmes();

    const modalElement = $("#modalFilmes");
    const modal = bootstrap.Modal.getInstance(modalElement);
    if (modal) {
        modal.hide();
    } else {
        $(modalElement).classList.remove('show');
        $(modalElement).style.display = 'none';
        $(modalElement).setAttribute('aria-hidden', 'true');
        $(modalElement).setAttribute('aria-modal', 'false');
        const body = document.body;
        body.classList.remove('modal-open');
        body.style.overflow = '';
        body.style.paddingRight = '';
        const backdrop = document.querySelector('.modal-backdrop');
        if (backdrop) backdrop.remove();
    }
    toggleSections(true);
}

const atualizarTabelaFilmes = () => {
    const tbody = $("#tabelaFilmesSelecionados tbody");
    tbody.innerHTML = locacaoState.filmesSelecionados.map((f, i) => `
        <tr>
            <td>${f.titulo}</td>
            <td>${f.idExemplar}</td>
            <td><button type="button" class="btn btn-danger btn-sm" onclick="removerLinha(${i})">Remover</button></td>
        </tr>
        <input type="hidden" name="exemplaresSelecionados" value="${f.idExemplar}">
        `
    ).join("");
};

const removerLinha = (index) => {
    locacaoState.filmesSelecionados.splice(index, 1);
    atualizarTabelaFilmes();
};

const voltarParaFilmes = () => toggleSections(true);

document.addEventListener("DOMContentLoaded", () => {
    if (typeof IMask !== 'undefined') {
        IMask($("#cpf"), { mask: "000.000.000-00" });
        IMask($("#telefone"), { mask: "(00) 00000-0000" });
    } else {
        console.error("IMask library not loaded.");
    }

    const modalFilmesElement = $("#modalFilmes");
    if(modalFilmesElement) {
        modalFilmesElement.addEventListener('hidden.bs.modal', function (event) {
            toggleSections(true);
        });
    }
});