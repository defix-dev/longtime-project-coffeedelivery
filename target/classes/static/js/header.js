$(document).ready(() => {
    $.ajax({
        url: "/authentication/check_account_status",
        type: "GET",
        success: (data) => {
            setViewLogoutStatus(data)
        }
    })
})

function setViewLogoutStatus(status) {
    const authNav = document.querySelector(".header__auth-nav");
    const afterAuthNav = document.querySelector(".header__after-auth");

    if(status) {
        authNav.style.display = "none";
        afterAuthNav.style.display = "flex";
    }
    else {
        authNav.style.display = "flex";
        afterAuthNav.style.display = "none";
    }
}

class ButtonDropdownExecutor {
    _dropdown;
    _buttonDOMObject;
    _arrowDOMObject;

    constructor(dropdown, buttonDOMObject, arrowDOMObject) {
        this._dropdown = dropdown;
        this._buttonDOMObject = buttonDOMObject;
        this._arrowDOMObject = arrowDOMObject;
        this.init();
    }

    init() {
        document.addEventListener("mousemove", (event) => {
            const buttonRect = this._buttonDOMObject.getBoundingClientRect();
            if(event.clientX >= buttonRect.left &&
            event.clientX <= buttonRect.right &&
            event.clientY >= buttonRect.top && event.clientY <= buttonRect.bottom) {
                this._arrowDOMObject.style.transform = "rotate(180deg)";
                this._dropdown.show();
            }
        });
    }

    tryHideDropdown(event) {
        if(!this._dropdown.isActivated)
            return;

        requestAnimationFrame(() => {
        const buttonRect = this._buttonDOMObject.getBoundingClientRect();
        if(event.clientX < buttonRect.left
            || event.clientX > buttonRect.right
            || event.clientY < buttonRect.top
            || event.clientY > buttonRect.bottom) {
            if (this._dropdown.tryHideDropdown(event))
                this._arrowDOMObject.style.transform = "rotate(0deg)";
        }});
    }
}

class Dropdown {
    _dropdownDOMObject;
    _dropdownHeaderTextDOMObject;

    constructor(isActivated = false,
                dropdownDOMObject,
                dropdownHeaderTextDOMObject) {
        this.isActivated = isActivated;
        this._dropdownDOMObject = dropdownDOMObject;
        this._dropdownHeaderTextDOMObject = dropdownHeaderTextDOMObject;
        this.init();
    }

    init() {
        if(!this.isActivated)
            this.hide();
        else
            this.show();
    }

    resize() {
        const header = document.querySelector(".header");
        const textRect = this._dropdownHeaderTextDOMObject.getBoundingClientRect();
        this._dropdownDOMObject.style.left = (textRect.left - 20) + "px";
        this._dropdownDOMObject.style.top = (textRect.top + (header.getBoundingClientRect().height - textRect.height)/2 + textRect.height) + "px";
    }

    tryHideDropdown(event) {
        const textRect = this._dropdownHeaderTextDOMObject.getBoundingClientRect();
        const dropdownRect = this._dropdownDOMObject.getBoundingClientRect();
        if(event.clientX < dropdownRect.left
            || event.clientX > dropdownRect.right
            || event.clientY < textRect.top
            || event.clientY > dropdownRect.bottom) {
            this.hide();
            return true;
        }

        return false;
    }

    show() {
        this._dropdownDOMObject.style.display = "flex";
        this.isActivated = true;
    }

    hide() {
        this._dropdownDOMObject.style.display = "none";
        this.isActivated = false;
    }
}

const dropdowns = [];
dropdowns.push(new Dropdown(
    true,
    document.querySelector(".order-panel"),
    document.querySelector(".header__text--order")
));
const buttons = [];
buttons.push(new ButtonDropdownExecutor(
   dropdowns[0],
    document.querySelector(".header__page-nav .header__text--order"),
    document.querySelector(".header__arrow")
));

function observeDropdownState(event) {
    for(const button of buttons) {
        button.tryHideDropdown(event);
    }
}

function resizeDropdown() {
    for(const dropdown of dropdowns) {
        dropdown.resize();
    }
}

window.addEventListener("resize", resizeDropdown);
document.addEventListener("DOMContentLoaded", resizeDropdown);
document.addEventListener("mousemove", observeDropdownState);