function filter(isReset) {

	let filters = [];

	if (!isReset) {

		let id = document.querySelector('#filterIdInput');
		let nameContains = document.querySelector('#filterNameInput');
		let required = document.querySelector('#filterRequiredInput');

		id.value && filters.push({'ids': id.value});
		nameContains.value && filters.push({'nameContains': nameContains.value});
		required.value && filters.push({'required': required.value});
	} else {
		resetForm('#filterForm');
	}

	customDataTable.setFilters(filters)
	dTable.order([[0]]).ajax.reload();
}

function create() {

	const createModal = $('#createModal');
	const createFromJQ = $('#createForm');
	const createElement = document.getElementById('createModal');
	const createForm = createElement.querySelector('#createForm');

	if (!isValidForm('#createForm')) {
		createFromJQ.addClass('was-validated');
	} else {

		showLoading();
		createFromJQ.removeClass('was-validated');

		axios.post('/v1/materials', {
			nameEs: createForm.querySelector('[name="nameEs"]').value,
			nameFr: createForm.querySelector('[name="nameFr"]').value,
			required: createForm.querySelector('[name="required"]').checked
		}).then((response) => {
			createFromJQ[0].reset();
			dTable.ajax.reload(function() { dTable.page(dTable.page()).draw(false); }, false);
			showNotify(messages.materials.create.success.replace('{0}', response.data.data.id));
		}).catch(error => catchError(error))
			.finally(() => {
				hideLoading();
				createModal.modal('hide');
			});
	}
}

function edit(id) {

	const editModal = $('#editModal');
	const saveBtn = $('#saveBtn');
	const editElement = document.getElementById('editModal');
	const editForm = editElement.querySelector('#editForm');

	axios.get('/v1/materials/' + id).then((response) => {
		console.log(response.data.data)
		editModal.find('#editNameEs').val(response.data.data.nameEs);
		editModal.find('#editNameFr').val(response.data.data.nameFr);
		editModal.find('#editRequiredCheckbox').prop('checked', response.data.data.required);
		editModal.modal('show');
	});

	saveBtn.off('click').on('click', function() {

		showLoading();

		axios.put('/v1/materials/' + id, {
			nameEs: editForm.querySelector('[name="nameEs"]').value,
			nameFr: editForm.querySelector('[name="nameFr"]').value,
			required: editForm.querySelector('[name="required"]').checked
		}).then((response) => {
			dTable.ajax.reload(function() { dTable.page(dTable.page()).draw(false); }, false);
			showNotify(messages.materials.update.success.replace('{0}', response.data.data.id));
		}).catch(error => catchError(error))
			.finally(() => {
				hideLoading();
				editModal.modal('hide');
			});
	});
}

function remove(id) {

	if (confirm(messages.materials.delete.alert)) {

		showLoading();

		axios.delete('/v1/materials/' + id).then(() => {
			dTable.ajax.reload(function() { dTable.page(dTable.page()).draw(false); }, false);
			showNotify(messages.materials.delete.success);
		}).catch(error => catchError(error))
			.finally(() => hideLoading());
	}
}
