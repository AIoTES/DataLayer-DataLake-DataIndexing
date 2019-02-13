import { element, by, promise, ElementFinder } from 'protractor';

export class DataIndexComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('sim-data-index div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class DataIndexUpdatePage {
    pageTitle = element(by.id('sim-data-index-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    indexIDInput = element(by.id('field_indexID'));
    indexByInput = element(by.id('field_indexBy'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setIndexIDInput(indexID): promise.Promise<void> {
        return this.indexIDInput.sendKeys(indexID);
    }

    getIndexIDInput() {
        return this.indexIDInput.getAttribute('value');
    }

    setIndexByInput(indexBy): promise.Promise<void> {
        return this.indexByInput.sendKeys(indexBy);
    }

    getIndexByInput() {
        return this.indexByInput.getAttribute('value');
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
