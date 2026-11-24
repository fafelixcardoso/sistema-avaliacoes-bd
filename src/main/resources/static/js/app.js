const API = '/api';
let c1 = null, c2 = null, c3 = null;

document.addEventListener('DOMContentLoaded', () => {
    carregarRelatorios();
    carregarDropdownsProva();
    carregarDadosMontagem();
    carregarQuestoesTodas();
    carregarUsuariosTodos();
});

// Navegação entre as Abas
function mudarAba(aba) {
    document.querySelectorAll('.nav-btn').forEach(b => b.classList.remove('active'));
    document.querySelectorAll('.aba-conteudo').forEach(p => p.classList.remove('active'));

    const btn = Array.from(document.querySelectorAll('.nav-btn')).find(b => b.getAttribute('onclick')?.includes(aba));
    if (btn) btn.classList.add('active');

    const pane = document.getElementById(`aba-${aba}`);
    if (pane) pane.classList.add('active');

    if (aba === 'relatorios') carregarRelatorios();
    if (aba === 'montar') carregarDadosMontagem();
    if (aba === 'questoes') carregarQuestoesTodas();
    if (aba === 'responder') carregarDropdownsProva();
    if (aba === 'usuarios') carregarUsuariosTodos();
}

function toast(msg) {
    const t = document.getElementById('toast');
    t.textContent = msg;
    t.style.display = 'block';
    setTimeout(() => { t.style.display = 'none'; }, 4000);
}

// 1. Relatórios analíticos
async function carregarRelatorios() {
    try {
        // Relatório 1: Desempenho e Médias
        const r1 = await (await fetch(`${API}/relatorios/desempenho`)).json();
        document.getElementById('tbody-r1').innerHTML = r1.map(x => `
            <tr>
                <td><strong>${x.avaliacao}</strong></td>
                <td>Prof. ${x.professorResponsavel}</td>
                <td>${x.totalRespostas}</td>
                <td style="color:var(--cor-primaria); font-weight:bold;">${Number(x.mediaNota).toFixed(2)}</td>
                <td>${Number(x.maiorNota).toFixed(2)}</td>
                <td>${Number(x.menorNota).toFixed(2)}</td>
                <td>${Number(x.taxaAdesaoPct).toFixed(1)}%</td>
            </tr>
        `).join('');

        if (c1) c1.destroy();
        c1 = new Chart(document.getElementById('chart-relatorio-1'), {
            type: 'bar',
            data: {
                labels: r1.map(x => x.avaliacao),
                datasets: [{ label: 'Média da Turma (0 a 10)', data: r1.map(x => x.mediaNota), backgroundColor: '#0F4D2A' }]
            },
            options: { responsive: true, maintainAspectRatio: false }
        });

        // Relatório 2: Pendentes e Gráfico Donut de Proporção
        const [pend, prop] = await Promise.all([
            (await fetch(`${API}/relatorios/pendentes`)).json(),
            (await fetch(`${API}/relatorios/proporcao`)).json()
        ]);

        document.getElementById('tbody-r2').innerHTML = pend.map(p => `
            <tr>
                <td><strong>${p.avaliacao}</strong></td>
                <td>${p.prazoLimite ? new Date(p.prazoLimite).toLocaleDateString('pt-BR') : '-'}</td>
                <td>${p.nomeAluno}</td>
                <td>${p.emailAluno}</td>
            </tr>
        `).join('') || '<tr><td colspan="4">Nenhuma pendência encontrada!</td></tr>';

        const respondidas = prop.reduce((a, b) => a + (parseInt(b.totalRespondidas) || 0), 0);
        const pendentes = prop.reduce((a, b) => a + (parseInt(b.totalPendentes) || 0), 0);

        if (c2) c2.destroy();
        c2 = new Chart(document.getElementById('chart-relatorio-2'), {
            type: 'doughnut',
            data: {
                labels: ['Respondidas', 'Pendentes'],
                datasets: [{ data: [respondidas, pendentes], backgroundColor: ['#10B981', '#F59E0B'] }]
            },
            options: { responsive: true, maintainAspectRatio: false }
        });

        // Relatório 3: Ranking de Questões Mais Difíceis
        const r3 = await (await fetch(`${API}/relatorios/questoes-dificeis`)).json();
        document.getElementById('tbody-r3').innerHTML = r3.map(q => `
            <tr>
                <td>#${q.idQuestao}</td>
                <td>${q.tipoQuestao}</td>
                <td>${q.enunciadoResumido}</td>
                <td>${q.totalRespostasAvaliadas}</td>
                <td>${Number(q.mediaObtida).toFixed(2)}</td>
                <td style="font-weight:bold; color:#B45309;">${Number(q.taxaAproveitamentoPct).toFixed(1)}%</td>
                <td>${Number(q.mediaGlobalSistema).toFixed(2)}</td>
            </tr>
        `).join('');

        if (c3) c3.destroy();
        c3 = new Chart(document.getElementById('chart-relatorio-3'), {
            type: 'bar',
            data: {
                labels: r3.map(q => `Q${q.idQuestao}`),
                datasets: [{ label: 'Aproveitamento (%)', data: r3.map(q => q.taxaAproveitamentoPct), backgroundColor: '#F59E0B' }]
            },
            options: { indexAxis: 'y', responsive: true, maintainAspectRatio: false }
        });

    } catch (e) {
        console.error('Erro nos relatórios:', e);
    }
}

// 2. Realizar avaliação
async function carregarDropdownsProva() {
    try {
        const [alunos, avs] = await Promise.all([
            (await fetch(`${API}/usuarios/alunos`)).json(),
            (await fetch(`${API}/avaliacoes`)).json()
        ]);

        document.getElementById('sel-aluno').innerHTML = '<option value="">Selecione um aluno...</option>' + 
            alunos.map(a => `<option value="${a.idUsuario}">${a.nome} (${a.email})</option>`).join('');

        document.getElementById('sel-avaliacao').innerHTML = '<option value="">Selecione uma avaliação...</option>' + 
            avs.map(a => `<option value="${a.idAvaliacao}">#${a.idAvaliacao} - ${a.titulo}</option>`).join('');
    } catch (e) {
        console.error(e);
    }
}

async function carregarQuestoesDaProva() {
    const idAv = document.getElementById('sel-avaliacao').value;
    const box = document.getElementById('box-prova');
    document.getElementById('box-resultado').style.display = 'none';

    if (!idAv) { box.style.display = 'none'; return; }

    const questoes = await (await fetch(`${API}/avaliacoes/${idAv}/questoes`)).json();
    document.getElementById('prova-titulo').textContent = `Avaliação #${idAv} — ${questoes.length} Questões`;

    document.getElementById('questoes-render').innerHTML = questoes.map((q, i) => {
        let opc = '';
        if (q.tipoQuestao === 'OBJETIVA') {
            opc = (q.alternativas || []).map(alt => `
                <label class="q-option">
                    <input type="radio" name="resp-q-${q.idQuestao}" value="${alt.letra}" required>
                    <strong>${alt.letra})</strong> ${alt.textoAlternativa}
                </label>
            `).join('');
        } else {
            opc = `<textarea id="resp-disc-${q.idQuestao}" rows="2" placeholder="Sua resposta dissertativa..."></textarea>`;
        }

        return `
            <div class="q-box">
                <h5>Questão ${i + 1} [${q.tipoQuestao}] — Valor: ${Number(q.valorQuestao).toFixed(2)} pts</h5>
                <p style="margin-bottom:0.5rem;">${q.enunciado}</p>
                ${opc}
            </div>
        `;
    }).join('');

    box.style.display = 'block';
}

async function enviarProva(e) {
    e.preventDefault();
    const idAv = parseInt(document.getElementById('sel-avaliacao').value);
    const idAluno = parseInt(document.getElementById('sel-aluno').value);

    if (!idAluno) return toast('Selecione o aluno que está realizando a prova!');

    const respostas = [];
    document.querySelectorAll('.q-box').forEach(box => {
        const radio = box.querySelector('input[type="radio"]:checked');
        const txt = box.querySelector('textarea');

        if (radio) {
            const idQ = parseInt(radio.name.replace('resp-q-', ''));
            respostas.push({ idQuestao: idQ, letraEscolhida: radio.value, textoResposta: null });
        } else if (txt) {
            const idQ = parseInt(txt.id.replace('resp-disc-', ''));
            respostas.push({ idQuestao: idQ, letraEscolhida: null, textoResposta: txt.value });
        }
    });

    try {
        const resp = await fetch(`${API}/respostas`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ idAvaliacao: idAv, idAluno: idAluno, respostas })
        });

        if (!resp.ok) throw new Error(await resp.text());
        const data = await resp.json();

        const resBox = document.getElementById('box-resultado');
        resBox.innerHTML = `
            <strong>✅ Avaliação Concluída com Sucesso!</strong><br>
            Pontuação obtida: <strong>${Number(data.notaFinal).toFixed(2)} pontos</strong>.<br>
            <small>Suas respostas foram registradas e sua pontuação foi processada pelo sistema.</small>
        `;
        resBox.style.display = 'block';
        toast('Avaliação concluída com sucesso!');
    } catch (err) {
        toast('Erro: ' + err.message);
    }
}

// 3. Montar avaliação
async function carregarDadosMontagem() {
    try {
        const [profs, questoes, alunos] = await Promise.all([
            (await fetch(`${API}/usuarios/professores`)).json(),
            (await fetch(`${API}/questoes`)).json(),
            (await fetch(`${API}/usuarios/alunos`)).json()
        ]);

        // Carrega professores
        const selProf = document.getElementById('m-professor');
        selProf.innerHTML = '<option value="">Selecione o professor...</option>' + 
            profs.map(p => `<option value="${p.idUsuario}">Prof. ${p.nome}</option>`).join('');

        // Carrega questões com campo para definir pontuação
        const boxQ = document.getElementById('m-lista-questoes');
        boxQ.innerHTML = questoes.map(q => `
            <div class="checklist-item">
                <input type="checkbox" id="chk-q-${q.idQuestao}" class="chk-questao-item" value="${q.idQuestao}">
                <label for="chk-q-${q.idQuestao}">#${q.idQuestao} [${q.tipoQuestao}] ${q.enunciado.substring(0, 65)}...</label>
                <div style="display:flex; align-items:center;">
                    <span style="font-size:0.75rem; color:#64748B;">Pts:</span>
                    <input type="number" id="val-q-${q.idQuestao}" min="0.1" max="10.0" step="0.5" value="1.0" title="Pontos desta questão">
                </div>
            </div>
        `).join('');

        // Carrega alunos para atribuição
        const boxA = document.getElementById('m-lista-alunos');
        boxA.innerHTML = alunos.map(a => `
            <div class="checklist-item">
                <input type="checkbox" id="chk-a-${a.idUsuario}" class="chk-aluno-item" value="${a.idUsuario}">
                <label for="chk-a-${a.idUsuario}">${a.nome} <small style="color:#64748B;">(${a.email})</small></label>
            </div>
        `).join('');

    } catch (e) {
        console.error('Erro ao carregar dados de montagem:', e);
    }
}

function marcarTodosAlunos() {
    const chks = document.querySelectorAll('.chk-aluno-item');
    const todos = Array.from(chks).every(c => c.checked);
    chks.forEach(c => c.checked = !todos);
}

async function salvarNovaAvaliacao(e) {
    e.preventDefault();
    const titulo = document.getElementById('m-titulo').value;
    const descricao = document.getElementById('m-descricao').value;
    const idProf = parseInt(document.getElementById('m-professor').value);
    const dtInicio = document.getElementById('m-inicio').value;
    const dtFim = document.getElementById('m-fim').value;

    // Coleta questões selecionadas com sua respectiva nota
    const questoes = [];
    document.querySelectorAll('.chk-questao-item:checked').forEach((chk, i) => {
        const idQ = parseInt(chk.value);
        const valQ = parseFloat(document.getElementById(`val-q-${idQ}`).value) || 1.0;
        questoes.push({ idQuestao: idQ, valorQuestao: valQ, numeroOrdem: i + 1 });
    });

    if (questoes.length === 0) return toast('Selecione pelo menos uma questão para a avaliação!');

    // Coleta alunos aos quais a prova será atribuída
    const alunosIds = [];
    document.querySelectorAll('.chk-aluno-item:checked').forEach(chk => {
        alunosIds.push(parseInt(chk.value));
    });

    if (alunosIds.length === 0) return toast('Selecione pelo menos um aluno para atribuir a avaliação!');

    const payload = {
        titulo: titulo,
        descricao: descricao,
        dataInicio: dtInicio,
        dataFim: dtFim,
        idProfessor: idProf,
        questoes: questoes,
        alunosIds: alunosIds
    };

    try {
        const resp = await fetch(`${API}/avaliacoes`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!resp.ok) throw new Error(await resp.text());
        toast('Avaliação publicada e atribuída com sucesso!');
        e.target.reset();
        carregarDropdownsProva();
    } catch (err) {
        toast('Erro ao criar avaliação: ' + err.message);
    }
}

// 4. Banco de questões
function alternarTipoQuestao() {
    const t = document.getElementById('q-tipo').value;
    document.getElementById('bloco-alternativas').style.display = t === 'OBJETIVA' ? 'block' : 'none';
    document.getElementById('bloco-discursiva').style.display = t === 'DISCURSIVA' ? 'block' : 'none';
    document.querySelectorAll('#bloco-alternativas input[type="text"]').forEach(i => i.required = (t === 'OBJETIVA'));
}

async function carregarQuestoesTodas() {
    try {
        const questoes = await (await fetch(`${API}/questoes`)).json();
        document.getElementById('tbody-questoes-todas').innerHTML = questoes.map(q => {
            let det = '';
            if (q.tipoQuestao === 'OBJETIVA') {
                const c = (q.alternativa || []).find(a => a.ehCorreta);
                det = `<strong>Correta: ${c ? c.letra : 'N/D'}</strong> (${(q.alternativa || []).length} opções)`;
            } else {
                det = `<em>${q.respostaEsperada ? q.respostaEsperada.substring(0, 60) + '...' : 'Livre'}</em>`;
            }
            return `
                <tr>
                    <td><strong>#${q.idQuestao}</strong></td>
                    <td><span style="font-size:0.75rem; background:#E2E8F0; padding:0.2rem 0.4rem; border-radius:3px;">${q.tipoQuestao}</span></td>
                    <td>${q.enunciado}</td>
                    <td><small style="color:#64748B;">${det}</small></td>
                </tr>
            `;
        }).join('');
    } catch (e) {
        console.error(e);
    }
}

async function salvarQuestao(e) {
    e.preventDefault();
    const tipo = document.getElementById('q-tipo').value;
    const enunciado = document.getElementById('q-enunciado').value;
    const gabarito = document.getElementById('q-esperada').value;

    const payload = {
        enunciado: enunciado,
        tipoQuestao: tipo,
        respostaEsperada: tipo === 'DISCURSIVA' ? gabarito : null,
        alternativa: []
    };

    if (tipo === 'OBJETIVA') {
        const correta = document.querySelector('input[name="alt-c"]:checked').value;
        ['A', 'B', 'C', 'D'].forEach(l => {
            payload.alternativa.push({
                letra: l,
                textoAlternativa: document.getElementById(`alt-${l}`).value,
                ehCorreta: (l === correta)
            });
        });
    }

    try {
        const res = await fetch(`${API}/questoes`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!res.ok) throw new Error(await res.text());
        toast('Questão cadastrada com sucesso!');
        e.target.reset();
        alternarTipoQuestao();
        carregarQuestoesTodas();
        carregarDadosMontagem();
    } catch (err) {
        toast('Erro ao salvar: ' + err.message);
    }
}

// =============================================================================
// ABA 5: USUÁRIOS (PROFESSORES E ALUNOS)
// =============================================================================
async function carregarUsuariosTodos() {
    try {
        const usuarios = await (await fetch(`${API}/usuarios`)).json();
        document.getElementById('tbody-usuarios-todos').innerHTML = usuarios.map(u => `
            <tr>
                <td><strong>#${u.idUsuario}</strong></td>
                <td>${u.nome}</td>
                <td>${u.email}</td>
                <td>
                    <span style="font-size:0.75rem; font-weight:600; padding:0.2rem 0.5rem; border-radius:4px; ${u.tipoUsuario === 'PROFESSOR' ? 'background:#DCFCE7; color:#15803D;' : 'background:#DBEAFE; color:#1D4ED8;'}">
                        ${u.tipoUsuario}
                    </span>
                </td>
            </tr>
        `).join('');
    } catch (e) {
        console.error(e);
    }
}

async function salvarUsuario(e) {
    e.preventDefault();
    const nome = document.getElementById('u-nome').value;
    const email = document.getElementById('u-email').value;
    const tipo = document.getElementById('u-tipo').value;

    try {
        const res = await fetch(`${API}/usuarios`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nome, email, tipoUsuario: tipo })
        });

        if (!res.ok) throw new Error(await res.text());
        toast(`Usuário ${nome} (${tipo}) cadastrado com sucesso!`);
        e.target.reset();
        carregarUsuariosTodos();
        carregarDadosMontagem();
        carregarDropdownsProva();
    } catch (err) {
        toast('Erro ao salvar usuário: ' + err.message);
    }
}
