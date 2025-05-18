document.addEventListener('DOMContentLoaded', () => {
    const icones = document.querySelectorAll('.exemplar-icon');

    icones.forEach(icone => {

        const elementoDoTooltip = icone.nextElementSibling;

        if (elementoDoTooltip && elementoDoTooltip.classList.contains('exemplar-tooltip')) {
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
});