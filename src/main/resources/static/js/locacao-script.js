document.addEventListener('DOMContentLoaded', () => {

    const exemplarIcones = document.querySelectorAll('.exemplar-icon');
    exemplarIcones.forEach(icone => {
        const elementoDoTooltip = icone.closest('td').querySelector('.exemplar-tooltip');
        if (elementoDoTooltip) {
            icone.addEventListener('mouseover', () => {
                const dimensoes = icone.getBoundingClientRect();

                elementoDoTooltip.style.top = (dimensoes.bottom + window.scrollY + 5) + 'px';
                elementoDoTooltip.style.left = (dimensoes.left + window.scrollX) + 'px';
                elementoDoTooltip.style.display = 'block';
            });
            icone.addEventListener('mouseout', () => {
                elementoDoTooltip.style.display = 'none';
            });
        }
    });


    const qrIcones = document.querySelectorAll('.qr-icon');
    qrIcones.forEach(icone => {
        const elementoDoTooltip = icone.closest('td').querySelector('.qr-tooltip');
        if (elementoDoTooltip) {
            icone.addEventListener('mouseover', () => {
                const dimensoes = icone.getBoundingClientRect();

                elementoDoTooltip.style.top = (dimensoes.bottom + window.scrollY + 5) + 'px';
                elementoDoTooltip.style.left = (dimensoes.left + window.scrollX) + 'px';
                elementoDoTooltip.style.display = 'block';
            });
            icone.addEventListener('mouseout', () => {
                elementoDoTooltip.style.display = 'none';
            });
        }
    });

    const cpfInput = document.querySelector("#termoInput");
    if (cpfInput) {

        if (typeof IMask !== 'undefined') {
             IMask(cpfInput, {
                mask: "000.000.000-00"
            });
        } else {
            console.error("IMask library not loaded.");
        }
    }
});
