/* ============================================================
   ROUTES API
   ============================================================

   GET    /api/products/book
   GET    /api/products/book/1

   GET    /api/products/video-game
   GET    /api/products/video-game/1

   GET    /api/products/dvd
   GET    /api/products/dvd/1

   POST   /api/products

   PUT    /api/products/book/1
   PUT    /api/products/video-game/1
   PUT    /api/products/dvd/1

   DELETE /api/products/book/1
   DELETE /api/products/video-game/1
   DELETE /api/products/dvd/1

   ============================================================ */


// ============================================================
// CONFIGURATION
// ============================================================

const API_URL = "/api/products";


// ============================================================
// ÉTAT DE L'APPLICATION
// ============================================================

let selectedProductType = null;
let selectedOperation = null;


// ============================================================
// ÉLÉMENTS HTML
// ============================================================

const productSelection =
    document.getElementById("product-selection");

const selectedProduct =
    document.getElementById("selected-product");

const selectedProductName =
    document.getElementById("selected-product-name");

const changeProductButton =
    document.getElementById("change-product-button");

const crudSection =
    document.getElementById("crud-section");

const operationPanel =
    document.getElementById("operation-panel");

const resultPanel =
    document.getElementById("result-panel");

const resultContent =
    document.getElementById("result-content");


// ============================================================
// INITIALISATION
// ============================================================

document.addEventListener("DOMContentLoaded", () => {

    initializeProductButtons();
    initializeCrudButtons();
    initializeChangeButton();

});


// ============================================================
// BOUTONS TYPE DE PRODUIT
// ============================================================

function initializeProductButtons() {

    const buttons =
        document.querySelectorAll(".product-type");

    buttons.forEach(button => {

        button.addEventListener("click", () => {

            const type =
                button.dataset.type;

            selectProductType(type);

        });

    });

}


// ============================================================
// BOUTONS CRUD
// ============================================================

function initializeCrudButtons() {

    const buttons =
        document.querySelectorAll(".crud-button");

    buttons.forEach(button => {

        button.addEventListener("click", () => {

            const operation =
                button.dataset.operation;

            selectOperation(operation);

        });

    });

}


// ============================================================
// BOUTON CHANGER DE TYPE
// ============================================================

function initializeChangeButton() {

    changeProductButton.addEventListener(
        "click",
        changeProductType
    );

}


// ============================================================
// SÉLECTION DU TYPE DE PRODUIT
// ============================================================

function selectProductType(type) {

    if (!isValidProductType(type)) {

        showResultMessage(
            "Type de produit invalide.",
            "error"
        );

        return;
    }

    selectedProductType = type;
    selectedOperation = null;

    document
        .querySelectorAll(".product-type")
        .forEach(button => {

            button.classList.remove("active");

        });

    const button =
        document.querySelector(
            `.product-type[data-type="${type}"]`
        );

    if (button) {

        button.classList.add("active");

    }

    selectedProductName.textContent =
        getProductLabel();

    selectedProduct.classList.remove("hidden");

    crudSection.classList.remove("hidden");

    operationPanel.classList.add("hidden");
    operationPanel.innerHTML = "";

    hideResult();

}


// ============================================================
// CHANGER DE TYPE DE PRODUIT
// ============================================================

function changeProductType() {

    selectedProductType = null;
    selectedOperation = null;

    document
        .querySelectorAll(".product-type")
        .forEach(button => {

            button.classList.remove("active");

        });

    document
        .querySelectorAll(".crud-button")
        .forEach(button => {

            button.classList.remove("active");

        });

    selectedProduct.classList.add("hidden");
    crudSection.classList.add("hidden");

    operationPanel.classList.add("hidden");
    operationPanel.innerHTML = "";

    hideResult();

}


// ============================================================
// SÉLECTION CRUD
// ============================================================

function selectOperation(operation) {

    if (!selectedProductType) {

        showResultMessage(
            "Veuillez d'abord sélectionner un type de produit.",
            "error"
        );

        return;
    }

    if (!isValidOperation(operation)) {

        showResultMessage(
            "Opération invalide.",
            "error"
        );

        return;
    }

    selectedOperation = operation;

    document
        .querySelectorAll(".crud-button")
        .forEach(button => {

            button.classList.remove("active");

        });

    const button =
        document.querySelector(
            `.crud-button[data-operation="${operation}"]`
        );

    if (button) {

        button.classList.add("active");

    }

    hideResult();

    switch (operation) {

        case "create":
            showCreateForm();
            break;

        case "read":
            showReadForm();
            break;

        case "update":
            showUpdateForm();
            break;

        case "delete":
            showDeleteForm();
            break;

    }

    operationPanel.classList.remove("hidden");

}


// ============================================================
// CONSTRUIRE URL D'UN PRODUIT PAR TYPE
// ============================================================

function getProductUrl(id) {

    return `${API_URL}/${selectedProductType}/${id}`;

}


// ============================================================
// CONSTRUIRE URL DE LA COLLECTION PAR TYPE
// ============================================================

function getProductsUrl() {

    return `${API_URL}/${selectedProductType}`;

}


// ============================================================
// FORMULAIRE CREATE
// ============================================================

function showCreateForm() {

    operationPanel.innerHTML = `

        <div class="operation-header">

            <h2>
                Créer un ${getProductLabel()}
            </h2>

            <p>
                Remplissez les informations du nouveau produit.
            </p>

        </div>

        <form
            id="create-form"
            class="product-form"
        >

            ${getCommonFields("create")}

            ${getSpecificFields("create")}

            <div class="form-actions">

                <button
                    type="submit"
                    class="action-button create-action"
                >
                    Créer le produit
                </button>

            </div>

        </form>

    `;

    document
        .getElementById("create-form")
        .addEventListener(
            "submit",
            createProduct
        );

}


// ============================================================
// CHAMPS COMMUNS
// ============================================================

function getCommonFields(prefix) {

    return `

        <div class="form-group">

            <label for="${prefix}-name">
                Nom *
            </label>

            <input
                type="text"
                id="${prefix}-name"
                placeholder="Nom du produit"
                maxlength="150"
                required
            >

        </div>

        <div class="form-group">

            <label for="${prefix}-price">
                Prix (€) *
            </label>

            <input
                type="number"
                id="${prefix}-price"
                placeholder="Ex : 19.99"
                min="0"
                step="0.01"
                required
            >

        </div>

        <div class="form-group full-width">

            <label for="${prefix}-description">
                Description
            </label>

            <textarea
                id="${prefix}-description"
                placeholder="Description du produit"
                maxlength="1000"
            ></textarea>

        </div>

    `;

}


// ============================================================
// CHAMPS SPÉCIFIQUES
// ============================================================

function getSpecificFields(prefix) {

    switch (selectedProductType) {

        // ----------------------------------------------------
        // LIVRE
        // ----------------------------------------------------

        case "book":

            return `

                <div class="form-group">

                    <label for="${prefix}-isbn">
                        ISBN
                    </label>

                    <input
                        type="text"
                        id="${prefix}-isbn"
                        placeholder="Ex : 9782070368228"
                        maxlength="20"
                    >

                </div>

                <div class="form-group">

                    <label for="${prefix}-author">
                        Auteur *
                    </label>

                    <input
                        type="text"
                        id="${prefix}-author"
                        placeholder="Nom de l'auteur"
                        maxlength="150"
                        required
                    >

                </div>

                <div class="form-group">

                    <label for="${prefix}-publisher">
                        Éditeur
                    </label>

                    <input
                        type="text"
                        id="${prefix}-publisher"
                        placeholder="Nom de l'éditeur"
                        maxlength="150"
                    >

                </div>

                <div class="form-group">

                    <label for="${prefix}-numberOfPages">
                        Nombre de pages *
                    </label>

                    <input
                        type="number"
                        id="${prefix}-numberOfPages"
                        placeholder="Ex : 250"
                        min="1"
                        step="1"
                        required
                    >

                </div>

            `;


        // ----------------------------------------------------
        // JEU VIDÉO
        // ----------------------------------------------------

        case "video-game":

            return `

                <div class="form-group">

                    <label for="${prefix}-developer">
                        Développeur *
                    </label>

                    <input
                        type="text"
                        id="${prefix}-developer"
                        placeholder="Nom du développeur"
                        maxlength="150"
                        required
                    >

                </div>

                <div class="form-group">

                    <label for="${prefix}-platform">
                        Plateforme *
                    </label>

                    <input
                        type="text"
                        id="${prefix}-platform"
                        placeholder="PC, PS5, Xbox..."
                        maxlength="100"
                        required
                    >

                </div>

                <div class="form-group">

                    <label for="${prefix}-genre">
                        Genre
                    </label>

                    <input
                        type="text"
                        id="${prefix}-genre"
                        placeholder="RPG, Action..."
                        maxlength="100"
                    >

                </div>

                <div class="form-group">

                    <label for="${prefix}-ageRating">
                        Classification d'âge
                    </label>

                    <input
                        type="text"
                        id="${prefix}-ageRating"
                        placeholder="PEGI 12"
                        maxlength="20"
                    >

                </div>

            `;


        // ----------------------------------------------------
        // DVD
        // ----------------------------------------------------

        case "dvd":

            return `

                <div class="form-group">

                    <label for="${prefix}-director">
                        Réalisateur *
                    </label>

                    <input
                        type="text"
                        id="${prefix}-director"
                        placeholder="Nom du réalisateur"
                        maxlength="150"
                        required
                    >

                </div>

                <div class="form-group">

                    <label for="${prefix}-duration">
                        Durée (minutes) *
                    </label>

                    <input
                        type="number"
                        id="${prefix}-duration"
                        placeholder="Ex : 120"
                        min="1"
                        step="1"
                        required
                    >

                </div>

                <div class="form-group">

                    <label for="${prefix}-releaseYear">
                        Année de sortie *
                    </label>

                    <input
                        type="number"
                        id="${prefix}-releaseYear"
                        placeholder="Ex : 2024"
                        min="1888"
                        max="2100"
                        step="1"
                        required
                    >

                </div>

            `;


        default:

            return "";

    }

}


// ============================================================
// CREATE
// ============================================================

async function createProduct(event) {

    event.preventDefault();

    const validation =
        validateProductForm("create");

    if (!validation.valid) {

        showResultMessage(
            validation.message,
            "error"
        );

        return;
    }

    const product =
        buildProductFromForm("create");

    try {

        /*
         * POST reste sur :
         *
         * /api/products
         */

        const response =
            await fetch(
                API_URL,
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body:
                        JSON.stringify(product)
                }
            );

        if (!response.ok) {

            throw new Error(
                await getApiError(response)
            );

        }

        const createdProduct =
            await response.json();

        showProductResult(
            "Produit créé avec succès.",
            createdProduct,
            "success"
        );

        document
            .getElementById("create-form")
            .reset();

    } catch (error) {

        showError(error);

    }

}


// ============================================================
// FORMULAIRE READ
// ============================================================

function showReadForm() {

    operationPanel.innerHTML = `

        <div class="operation-header">

            <h2>
                Lire un ${getProductLabel()}
            </h2>

            <p>
                Recherchez un produit grâce à son identifiant
                ou affichez tous les ${getProductLabel()}s.
            </p>

        </div>

        <form id="read-one-form">

            <div class="form-group">

                <label for="read-id">
                    ID du ${getProductLabel()} *
                </label>

                <input
                    type="number"
                    id="read-id"
                    placeholder="Ex : 1"
                    min="1"
                    step="1"
                    required
                >

            </div>

            <br>

            <button
                type="submit"
                class="action-button read-action"
            >
                🔍 Rechercher
            </button>

        </form>

        <br>

        <button
            type="button"
            id="read-all-button"
            class="action-button read-action"
        >
            Afficher tous les ${getProductLabel()}s
        </button>

    `;

    document
        .getElementById("read-one-form")
        .addEventListener(
            "submit",
            getProductById
        );

    document
        .getElementById("read-all-button")
        .addEventListener(
            "click",
            getAllProducts
        );

}


// ============================================================
// READ ONE
// ============================================================

async function getProductById(event) {

    event.preventDefault();

    const id =
        document
            .getElementById("read-id")
            .value;

    const validation =
        validateId(id);

    if (!validation.valid) {

        showResultMessage(
            validation.message,
            "error"
        );

        return;
    }

    try {

        /*
         * GET :
         *
         * /api/products/book/1
         * /api/products/video-game/1
         * /api/products/dvd/1
         */

        const response =
            await fetch(
                getProductUrl(id)
            );

        if (response.status === 404) {

            showResultMessage(
                `Aucun ${getProductLabel()} trouvé avec l'ID ${id}.`,
                "error"
            );

            return;
        }

        if (!response.ok) {

            throw new Error(
                await getApiError(response)
            );

        }

        const product =
            await response.json();

        showProductResult(
            `${getProductLabel()} trouvé.`,
            product,
            "success"
        );

    } catch (error) {

        showError(error);

    }

}


// ============================================================
// READ ALL
// ============================================================

async function getAllProducts() {

    try {

        /*
         * IMPORTANT :
         *
         * Avant :
         * GET /api/products
         * puis filtre JavaScript.
         *
         * Maintenant :
         *
         * GET /api/products/book
         * GET /api/products/video-game
         * GET /api/products/dvd
         *
         * Le filtrage est donc fait par le backend.
         */

        const response =
            await fetch(
                getProductsUrl()
            );

        if (response.status === 404) {

            showResultMessage(
                `Aucun ${getProductLabel()} trouvé.`,
                "info"
            );

            return;
        }

        if (!response.ok) {

            throw new Error(
                await getApiError(response)
            );

        }

        const products =
            await response.json();

        showProductsTable(products);

    } catch (error) {

        showError(error);

    }

}


// ============================================================
// FORMULAIRE UPDATE
// ============================================================

function showUpdateForm() {

    operationPanel.innerHTML = `

        <div class="operation-header">

            <h2>
                Modifier un ${getProductLabel()}
            </h2>

            <p>
                Saisissez l'ID, chargez le produit,
                puis modifiez ses informations.
            </p>

        </div>

        <form
            id="update-form"
            class="product-form"
        >

            <div class="form-group full-width">

                <label for="update-id">
                    ID du ${getProductLabel()} *
                </label>

                <input
                    type="number"
                    id="update-id"
                    placeholder="Ex : 1"
                    min="1"
                    step="1"
                    required
                >

            </div>

            ${getCommonFields("update")}

            ${getSpecificFields("update")}

            <div class="form-actions">

                <button
                    type="button"
                    id="load-update-button"
                    class="action-button load-button"
                >
                    Charger le produit
                </button>

                <button
                    type="submit"
                    class="action-button update-action"
                >
                    Modifier le produit
                </button>

            </div>

        </form>

    `;

    document
        .getElementById("load-update-button")
        .addEventListener(
            "click",
            loadProductForUpdate
        );

    document
        .getElementById("update-form")
        .addEventListener(
            "submit",
            updateProduct
        );

}


// ============================================================
// CHARGER UN PRODUIT
// ============================================================

async function loadProductForUpdate() {

    const id =
        document
            .getElementById("update-id")
            .value;

    const validation =
        validateId(id);

    if (!validation.valid) {

        showResultMessage(
            validation.message,
            "error"
        );

        return;
    }

    try {

        /*
         * Le type est directement présent dans l'URL.
         *
         * Exemple :
         *
         * GET /api/products/book/1
         */

        const response =
            await fetch(
                getProductUrl(id)
            );

        if (response.status === 404) {

            showResultMessage(
                `Aucun ${getProductLabel()} trouvé avec l'ID ${id}.`,
                "error"
            );

            return;
        }

        if (!response.ok) {

            throw new Error(
                await getApiError(response)
            );

        }

        const product =
            await response.json();

        fillUpdateForm(product);

        showResultMessage(
            "Produit chargé. Vous pouvez maintenant le modifier.",
            "success"
        );

    } catch (error) {

        showError(error);

    }

}


// ============================================================
// REMPLIR LE FORMULAIRE UPDATE
// ============================================================

function fillUpdateForm(product) {

    document
        .getElementById("update-name")
        .value =
        product.name || "";

    document
        .getElementById("update-price")
        .value =
        product.price ?? "";

    document
        .getElementById("update-description")
        .value =
        product.description || "";

    switch (selectedProductType) {

        case "book":

            document
                .getElementById("update-isbn")
                .value =
                product.isbn || "";

            document
                .getElementById("update-author")
                .value =
                product.author || "";

            document
                .getElementById("update-publisher")
                .value =
                product.publisher || "";

            document
                .getElementById("update-numberOfPages")
                .value =
                product.numberOfPages ?? "";

            break;


        case "video-game":

            document
                .getElementById("update-developer")
                .value =
                product.developer || "";

            document
                .getElementById("update-platform")
                .value =
                product.platform || "";

            document
                .getElementById("update-genre")
                .value =
                product.genre || "";

            document
                .getElementById("update-ageRating")
                .value =
                product.ageRating || "";

            break;


        case "dvd":

            document
                .getElementById("update-director")
                .value =
                product.director || "";

            document
                .getElementById("update-duration")
                .value =
                product.duration ?? "";

            document
                .getElementById("update-releaseYear")
                .value =
                product.releaseYear ?? "";

            break;

    }

}


// ============================================================
// UPDATE
// ============================================================

async function updateProduct(event) {

    event.preventDefault();

    const id =
        document
            .getElementById("update-id")
            .value;

    const idValidation =
        validateId(id);

    if (!idValidation.valid) {

        showResultMessage(
            idValidation.message,
            "error"
        );

        return;
    }

    const validation =
        validateProductForm("update");

    if (!validation.valid) {

        showResultMessage(
            validation.message,
            "error"
        );

        return;
    }

    const product =
        buildProductFromForm("update");

    try {

        /*
         * PUT :
         *
         * /api/products/book/1
         * /api/products/video-game/1
         * /api/products/dvd/1
         */

        const response =
            await fetch(
                getProductUrl(id),
                {
                    method: "PUT",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body:
                        JSON.stringify(product)
                }
            );

        if (response.status === 404) {

            showResultMessage(
                `Aucun ${getProductLabel()} trouvé avec l'ID ${id}.`,
                "error"
            );

            return;
        }

        if (!response.ok) {

            throw new Error(
                await getApiError(response)
            );

        }

        const updatedProduct =
            await response.json();

        showProductResult(
            "Produit modifié avec succès.",
            updatedProduct,
            "success"
        );

    } catch (error) {

        showError(error);

    }

}


// ============================================================
// FORMULAIRE DELETE
// ============================================================

function showDeleteForm() {

    operationPanel.innerHTML = `

        <div class="operation-header">

            <h2>
                Supprimer un ${getProductLabel()}
            </h2>

            <p>
                Entrez l'identifiant du ${getProductLabel()} à supprimer.
            </p>

        </div>

        <form id="delete-form">

            <div class="form-group">

                <label for="delete-id">
                    ID du ${getProductLabel()} *
                </label>

                <input
                    type="number"
                    id="delete-id"
                    placeholder="Ex : 1"
                    min="1"
                    step="1"
                    required
                >

            </div>

            <br>

            <div class="warning">

                ⚠️ Cette opération est irréversible.

            </div>

            <button
                type="submit"
                class="action-button delete-action"
            >
                Supprimer le produit
            </button>

        </form>

    `;

    document
        .getElementById("delete-form")
        .addEventListener(
            "submit",
            deleteProduct
        );

}


// ============================================================
// DELETE
// ============================================================

async function deleteProduct(event) {

    event.preventDefault();

    const id =
        document
            .getElementById("delete-id")
            .value;

    const validation =
        validateId(id);

    if (!validation.valid) {

        showResultMessage(
            validation.message,
            "error"
        );

        return;
    }

    const confirmation =
        confirm(
            `Voulez-vous vraiment supprimer le ${getProductLabel()} ${id} ?`
        );

    if (!confirmation) {

        return;
    }

    try {

        /*
         * DELETE :
         *
         * /api/products/book/1
         * /api/products/video-game/1
         * /api/products/dvd/1
         */

        const response =
            await fetch(
                getProductUrl(id),
                {
                    method: "DELETE"
                }
            );

        if (response.status === 404) {

            showResultMessage(
                `Aucun ${getProductLabel()} trouvé avec l'ID ${id}.`,
                "error"
            );

            return;
        }

        if (!response.ok) {

            throw new Error(
                await getApiError(response)
            );

        }

        showResultMessage(
            `Le ${getProductLabel()} ${id} a été supprimé avec succès.`,
            "success"
        );

        document
            .getElementById("delete-form")
            .reset();

    } catch (error) {

        showError(error);

    }

}


// ============================================================
// CONSTRUIRE LE PRODUIT
// ============================================================

function buildProductFromForm(prefix) {

    const product = {

        /*
         * Le type est envoyé dans le JSON pour le POST
         * et reste compatible avec le backend.
         */

        type:
            selectedProductType,

        name:
            document
                .getElementById(`${prefix}-name`)
                .value
                .trim(),

        price:
            Number(
                document
                    .getElementById(`${prefix}-price`)
                    .value
            ),

        description:
            document
                .getElementById(`${prefix}-description`)
                .value
                .trim()

    };


    switch (selectedProductType) {

        // ----------------------------------------------------
        // LIVRE
        // ----------------------------------------------------

        case "book":

            product.isbn =
                document
                    .getElementById(`${prefix}-isbn`)
                    .value
                    .trim();

            product.author =
                document
                    .getElementById(`${prefix}-author`)
                    .value
                    .trim();

            product.publisher =
                document
                    .getElementById(`${prefix}-publisher`)
                    .value
                    .trim();

            product.numberOfPages =
                Number(
                    document
                        .getElementById(`${prefix}-numberOfPages`)
                        .value
                );

            break;


        // ----------------------------------------------------
        // JEU VIDÉO
        // ----------------------------------------------------

        case "video-game":

            product.developer =
                document
                    .getElementById(`${prefix}-developer`)
                    .value
                    .trim();

            product.platform =
                document
                    .getElementById(`${prefix}-platform`)
                    .value
                    .trim();

            product.genre =
                document
                    .getElementById(`${prefix}-genre`)
                    .value
                    .trim();

            product.ageRating =
                document
                    .getElementById(`${prefix}-ageRating`)
                    .value
                    .trim();

            break;


        // ----------------------------------------------------
        // DVD
        // ----------------------------------------------------

        case "dvd":

            product.director =
                document
                    .getElementById(`${prefix}-director`)
                    .value
                    .trim();

            product.duration =
                Number(
                    document
                        .getElementById(`${prefix}-duration`)
                        .value
                );

            product.releaseYear =
                Number(
                    document
                        .getElementById(`${prefix}-releaseYear`)
                        .value
                );

            break;

    }

    return product;

}


// ============================================================
// VALIDATION GÉNÉRALE
// ============================================================

function validateProductForm(prefix) {

    if (!selectedProductType) {

        return {
            valid: false,
            message:
                "Veuillez sélectionner un type de produit."
        };

    }

    const name =
        document
            .getElementById(`${prefix}-name`)
            .value
            .trim();

    const priceValue =
        document
            .getElementById(`${prefix}-price`)
            .value;

    const price =
        Number(priceValue);

    const description =
        document
            .getElementById(`${prefix}-description`)
            .value
            .trim();


    // --------------------------------------------------------
    // NOM
    // --------------------------------------------------------

    if (!name) {

        return {
            valid: false,
            message:
                "Le nom du produit est obligatoire."
        };

    }

    if (name.length < 2) {

        return {
            valid: false,
            message:
                "Le nom doit contenir au moins 2 caractères."
        };

    }

    if (name.length > 150) {

        return {
            valid: false,
            message:
                "Le nom ne peut pas dépasser 150 caractères."
        };

    }


    // --------------------------------------------------------
    // PRIX
    // --------------------------------------------------------

    if (!priceValue) {

        return {
            valid: false,
            message:
                "Le prix est obligatoire."
        };

    }

    if (!Number.isFinite(price)) {

        return {
            valid: false,
            message:
                "Le prix doit être un nombre valide."
        };

    }

    if (price < 0) {

        return {
            valid: false,
            message:
                "Le prix ne peut pas être négatif."
        };

    }

    if (price > 99999999) {

        return {
            valid: false,
            message:
                "Le prix est trop élevé."
        };

    }


    // --------------------------------------------------------
    // DESCRIPTION
    // --------------------------------------------------------

    if (description.length > 1000) {

        return {
            valid: false,
            message:
                "La description ne peut pas dépasser 1000 caractères."
        };

    }


    // --------------------------------------------------------
    // VALIDATION SPÉCIFIQUE
    // --------------------------------------------------------

    switch (selectedProductType) {

        case "book":

            return validateBook(prefix);

        case "video-game":

            return validateVideoGame(prefix);

        case "dvd":

            return validateDVD(prefix);

        default:

            return {
                valid: false,
                message:
                    "Type de produit invalide."
            };

    }

}


// ============================================================
// VALIDATION LIVRE
// ============================================================

function validateBook(prefix) {

    const isbn =
        document
            .getElementById(`${prefix}-isbn`)
            .value
            .trim();

    const author =
        document
            .getElementById(`${prefix}-author`)
            .value
            .trim();

    const publisher =
        document
            .getElementById(`${prefix}-publisher`)
            .value
            .trim();

    const pagesValue =
        document
            .getElementById(`${prefix}-numberOfPages`)
            .value;

    const pages =
        Number(pagesValue);


    if (isbn.length > 20) {

        return {
            valid: false,
            message:
                "L'ISBN ne peut pas dépasser 20 caractères."
        };

    }

    if (!author) {

        return {
            valid: false,
            message:
                "L'auteur est obligatoire."
        };

    }

    if (author.length > 150) {

        return {
            valid: false,
            message:
                "Le nom de l'auteur ne peut pas dépasser 150 caractères."
        };

    }

    if (publisher.length > 150) {

        return {
            valid: false,
            message:
                "Le nom de l'éditeur ne peut pas dépasser 150 caractères."
        };

    }

    if (!pagesValue) {

        return {
            valid: false,
            message:
                "Le nombre de pages est obligatoire."
        };

    }

    if (
        !Number.isInteger(pages) ||
        pages <= 0
    ) {

        return {
            valid: false,
            message:
                "Le nombre de pages doit être un entier supérieur à 0."
        };

    }

    return {
        valid: true
    };

}


// ============================================================
// VALIDATION JEU VIDÉO
// ============================================================

function validateVideoGame(prefix) {

    const developer =
        document
            .getElementById(`${prefix}-developer`)
            .value
            .trim();

    const platform =
        document
            .getElementById(`${prefix}-platform`)
            .value
            .trim();

    const genre =
        document
            .getElementById(`${prefix}-genre`)
            .value
            .trim();

    const ageRating =
        document
            .getElementById(`${prefix}-ageRating`)
            .value
            .trim();


    if (!developer) {

        return {
            valid: false,
            message:
                "Le développeur est obligatoire."
        };

    }

    if (developer.length > 150) {

        return {
            valid: false,
            message:
                "Le nom du développeur ne peut pas dépasser 150 caractères."
        };

    }

    if (!platform) {

        return {
            valid: false,
            message:
                "La plateforme est obligatoire."
        };

    }

    if (platform.length > 100) {

        return {
            valid: false,
            message:
                "La plateforme ne peut pas dépasser 100 caractères."
        };

    }

    if (genre.length > 100) {

        return {
            valid: false,
            message:
                "Le genre ne peut pas dépasser 100 caractères."
        };

    }

    if (ageRating.length > 20) {

        return {
            valid: false,
            message:
                "La classification d'âge ne peut pas dépasser 20 caractères."
        };

    }

    return {
        valid: true
    };

}


// ============================================================
// VALIDATION DVD
// ============================================================

function validateDVD(prefix) {

    const director =
        document
            .getElementById(`${prefix}-director`)
            .value
            .trim();

    const durationValue =
        document
            .getElementById(`${prefix}-duration`)
            .value;

    const yearValue =
        document
            .getElementById(`${prefix}-releaseYear`)
            .value;

    const duration =
        Number(durationValue);

    const year =
        Number(yearValue);


    if (!director) {

        return {
            valid: false,
            message:
                "Le réalisateur est obligatoire."
        };

    }

    if (director.length > 150) {

        return {
            valid: false,
            message:
                "Le nom du réalisateur ne peut pas dépasser 150 caractères."
        };

    }

    if (!durationValue) {

        return {
            valid: false,
            message:
                "La durée est obligatoire."
        };

    }

    if (
        !Number.isInteger(duration) ||
        duration <= 0
    ) {

        return {
            valid: false,
            message:
                "La durée doit être un entier supérieur à 0."
        };

    }

    if (!yearValue) {

        return {
            valid: false,
            message:
                "L'année de sortie est obligatoire."
        };

    }

    if (
        !Number.isInteger(year) ||
        year < 1888 ||
        year > 2100
    ) {

        return {
            valid: false,
            message:
                "L'année de sortie doit être comprise entre 1888 et 2100."
        };

    }

    return {
        valid: true
    };

}


// ============================================================
// VALIDATION ID
// ============================================================

function validateId(id) {

    if (!id) {

        return {
            valid: false,
            message:
                "L'identifiant est obligatoire."
        };

    }

    const number =
        Number(id);

    if (
        !Number.isInteger(number) ||
        number <= 0
    ) {

        return {
            valid: false,
            message:
                "L'identifiant doit être un entier supérieur à 0."
        };

    }

    return {
        valid: true
    };

}


// ============================================================
// VALIDATION TYPE
// ============================================================

function isValidProductType(type) {

    return [
        "book",
        "video-game",
        "dvd"
    ].includes(type);

}


// ============================================================
// VALIDATION OPÉRATION
// ============================================================

function isValidOperation(operation) {

    return [
        "create",
        "read",
        "update",
        "delete"
    ].includes(operation);

}


// ============================================================
// LABEL DU PRODUIT
// ============================================================

function getProductLabel() {

    switch (selectedProductType) {

        case "book":
            return "livre";

        case "video-game":
            return "jeu vidéo";

        case "dvd":
            return "DVD";

        default:
            return "produit";

    }

}


// ============================================================
// AFFICHAGE D'UN PRODUIT
// ============================================================

function showProductResult(
    title,
    product,
    type
) {

    resultPanel.classList.remove("hidden");

    let specificFields = "";

    const productType =
        product.type ||
        selectedProductType;


    switch (productType) {

        // ----------------------------------------------------
        // LIVRE
        // ----------------------------------------------------

        case "book":

            specificFields = `

                <div class="product-field">

                    <span>ISBN</span>

                    <strong>
                        ${escapeHtml(
                            product.isbn || "-"
                        )}
                    </strong>

                </div>

                <div class="product-field">

                    <span>Auteur</span>

                    <strong>
                        ${escapeHtml(
                            product.author || "-"
                        )}
                    </strong>

                </div>

                <div class="product-field">

                    <span>Éditeur</span>

                    <strong>
                        ${escapeHtml(
                            product.publisher || "-"
                        )}
                    </strong>

                </div>

                <div class="product-field">

                    <span>Nombre de pages</span>

                    <strong>
                        ${product.numberOfPages ?? "-"}
                    </strong>

                </div>

            `;

            break;


        // ----------------------------------------------------
        // JEU VIDÉO
        // ----------------------------------------------------

        case "video-game":

            specificFields = `

                <div class="product-field">

                    <span>Développeur</span>

                    <strong>
                        ${escapeHtml(
                            product.developer || "-"
                        )}
                    </strong>

                </div>

                <div class="product-field">

                    <span>Plateforme</span>

                    <strong>
                        ${escapeHtml(
                            product.platform || "-"
                        )}
                    </strong>

                </div>

                <div class="product-field">

                    <span>Genre</span>

                    <strong>
                        ${escapeHtml(
                            product.genre || "-"
                        )}
                    </strong>

                </div>

                <div class="product-field">

                    <span>Classification</span>

                    <strong>
                        ${escapeHtml(
                            product.ageRating || "-"
                        )}
                    </strong>

                </div>

            `;

            break;


        // ----------------------------------------------------
        // DVD
        // ----------------------------------------------------

        case "dvd":

            specificFields = `

                <div class="product-field">

                    <span>Réalisateur</span>

                    <strong>
                        ${escapeHtml(
                            product.director || "-"
                        )}
                    </strong>

                </div>

                <div class="product-field">

                    <span>Durée</span>

                    <strong>
                        ${product.duration ?? "-"} min
                    </strong>

                </div>

                <div class="product-field">

                    <span>Année de sortie</span>

                    <strong>
                        ${product.releaseYear ?? "-"}
                    </strong>

                </div>

            `;

            break;

    }


    resultContent.innerHTML = `

        <div class="message ${type}">
            ${escapeHtml(title)}
        </div>

        <div class="product-card">

            <div class="product-field">

                <span>ID</span>

                <strong>
                    ${product.id ?? "-"}
                </strong>

            </div>

            <div class="product-field">

                <span>Type</span>

                <strong>
                    ${escapeHtml(
                        product.type ||
                        selectedProductType ||
                        "-"
                    )}
                </strong>

            </div>

            <div class="product-field">

                <span>Nom</span>

                <strong>
                    ${escapeHtml(
                        product.name || "-"
                    )}
                </strong>

            </div>

            <div class="product-field">

                <span>Prix</span>

                <strong>
                    ${product.price ?? "-"} €
                </strong>

            </div>

            <div class="product-field description-field">

                <span>Description</span>

                <strong>
                    ${escapeHtml(
                        product.description || "-"
                    )}
                </strong>

            </div>

            ${specificFields}

        </div>

    `;

}


// ============================================================
// AFFICHAGE DE TOUS LES PRODUITS
// ============================================================

function showProductsTable(products) {

    resultPanel.classList.remove("hidden");


    if (!Array.isArray(products)) {

        showResultMessage(
            "Les données reçues de l'API sont invalides.",
            "error"
        );

        return;
    }


    if (products.length === 0) {

        resultContent.innerHTML = `

            <div class="message info">

                Aucun ${getProductLabel()} trouvé.

            </div>

        `;

        return;
    }


    let rows = "";


    products.forEach(product => {

        rows += `

            <tr>

                <td>
                    ${product.id ?? "-"}
                </td>

                <td>
                    ${escapeHtml(
                        product.type || "-"
                    )}
                </td>

                <td>
                    ${escapeHtml(
                        product.name || "-"
                    )}
                </td>

                <td>
                    ${product.price ?? "-"} €
                </td>

                <td>
                    ${escapeHtml(
                        getSpecificSummary(product)
                    )}
                </td>

                <td>
                    ${escapeHtml(
                        product.description || "-"
                    )}
                </td>

            </tr>

        `;

    });


    resultContent.innerHTML = `

        <div class="message success">

            ${products.length}
            produit${products.length > 1 ? "s" : ""}
            trouvé${products.length > 1 ? "s" : ""}.

        </div>

        <div class="table-container">

            <table>

                <thead>

                    <tr>

                        <th>ID</th>

                        <th>Type</th>

                        <th>Nom</th>

                        <th>Prix</th>

                        <th>Informations</th>

                        <th>Description</th>

                    </tr>

                </thead>

                <tbody>

                    ${rows}

                </tbody>

            </table>

        </div>

    `;

}


// ============================================================
// INFORMATIONS SPÉCIFIQUES POUR LE TABLEAU
// ============================================================

function getSpecificSummary(product) {

    const type =
        product.type ||
        selectedProductType;


    switch (type) {

        case "book":

            return (
                `ISBN : ${product.isbn || "-"} | ` +
                `Auteur : ${product.author || "-"} | ` +
                `Pages : ${product.numberOfPages ?? "-"}`
            );


        case "video-game":

            return (
                `Développeur : ${product.developer || "-"} | ` +
                `Plateforme : ${product.platform || "-"}`
            );


        case "dvd":

            return (
                `Réalisateur : ${product.director || "-"} | ` +
                `Durée : ${product.duration ?? "-"} min`
            );


        default:

            return "-";

    }

}


// ============================================================
// MESSAGE
// ============================================================

function showResultMessage(
    message,
    type
) {

    resultPanel.classList.remove("hidden");

    resultContent.innerHTML = `

        <div class="message ${type}">
            ${escapeHtml(message)}
        </div>

    `;

}


// ============================================================
// ERREUR
// ============================================================

function showError(error) {

    console.error(error);

    showResultMessage(
        error.message ||
        "Une erreur est survenue lors de l'opération.",
        "error"
    );

}


// ============================================================
// ERREUR API
// ============================================================

async function getApiError(response) {

    try {

        const data =
            await response.json();

        if (data.message) {

            return data.message;

        }

        if (data.error) {

            return data.error;

        }

        return `Erreur HTTP ${response.status}`;

    } catch (error) {

        return `Erreur HTTP ${response.status}`;

    }

}


// ============================================================
// CACHER LES RÉSULTATS
// ============================================================

function hideResult() {

    resultPanel.classList.add("hidden");

    resultContent.innerHTML = "";

}


// ============================================================
// PROTECTION CONTRE HTML / XSS
// ============================================================

function escapeHtml(value) {

    const div =
        document.createElement("div");

    div.textContent =
        String(value);

    return div.innerHTML;

}
