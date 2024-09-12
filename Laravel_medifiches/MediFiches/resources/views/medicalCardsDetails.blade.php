<x-app-layout>
    <div class="postition-relative">
        <div class="container mt-5 position-absolute bg-white" id="consult">
            @foreach ($data as $row)
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h3 class="pb-3">Fiche médicale <strong> {{ $row->last_name . ' ' . $row->first_name }}</strong>
                    </h3>
                    <form method="POST" action="{{ route('generate-pdf') }}" class="ms-auto">

                        <input type="text" name="national_number" value="{{ $row->national_number }}" hidden>
                        @csrf
                        <button type="submit" class="btn" style="background: none; border: none;"
                            title="Télécharger en PDF">
                            <img src="{{ asset('images/down.png') }}" alt="Générer PDF" style="height: 40px;">
                        </button>
                    </form>
                    @if (Auth::user()->role == 'Parent')
                    <x-button class="edit">Modifier</x-button>
                    <div class="editMode" id="editActions">
                        <x-button>Annuler</x-button>
                    </div>
                    @endif
                </div>
                <x-validation-errors class="mb-4" />
                <ul class="list-group list-group-flush edit">
                    @foreach ($fields as $field)
                        <li class="list-group-item"><strong>{{ $field['label'] }} : </strong>{{ $row->{$field['name']} }}
                        </li>
                    @endforeach
                </ul>
                <ul class="list-group list-group-flush editMode">
                    <form action="{{ route('edit_record') }}" method="post" id="editRecordForm">
                        @csrf
                        @foreach ($fields as $field)
                            <x-label for="{{ $field['name'] }}" value="{{ __($field['label']) }}" />
                            @if ($field['name'] === 'national_number')
                            <x-input id="{{ $field['name'] }}" class="block mt-1 w-full"
                                    type="{{ $field['type'] }}" name="{{ $field['name'] }}"
                                    autofocus autocomplete="{{ $field['name'] }}"
                                    placeholder="{{ __($field['placeholder'] ?? '') }}"
                                    value="{{ $row->{$field['name']} }}" readonly/>
                            
                            @elseif ($field['type'] === 'checkbox')
                                <x-input name="{{ $field['name'] }}" type="hidden" value="0"/>
                                @if ($row->{$field['name']})
                                    <x-input id="{{ $field['name'] }}" class="block mt-1" type="{{ $field['type'] }}"
                                        name="{{ $field['name'] }}" checked value="1" />
                                @else
                                    <x-input id="{{ $field['name'] }}" class="block mt-1" type="{{ $field['type'] }}"
                                        name="{{ $field['name'] }}" value="1" />
                                @endif
                            @elseif(isset($field['isTextArea']))
                                <textarea id="{{ $field['name'] }}" class="block mt-1 w-full" type="{{ $field['type'] }}" name="{{ $field['name'] }}"
                                    :value="old(''.$field['name'])">{{ $row->{$field['name']} }}</textarea>
                            @else
                                <x-input id="{{ $field['name'] }}" class="block mt-1 w-full"
                                    type="{{ $field['type'] }}" name="{{ $field['name'] }}" :value="old('' . $field['name'])"
                                    autofocus autocomplete="{{ $field['name'] }}"
                                    placeholder="{{ __($field['placeholder'] ?? '') }}"
                                    value="{{ $row->{$field['name']} }}" />
                            @endif
                        @endforeach
                        <x-button type="submit" id="editSubmit">Sauvegarder</x-button>
                    </form>
                </ul>
            @endforeach
        </div>
    </div>

    <style>
        .editMode {
            display: none;
        }
    </style>

<script>
    $(document).ready(()=> {
        $('#editSubmit').appendTo('#editActions')
        $("button.edit").on('click', () => {
            $('.edit').toggle()
            $('.editMode').toggle()
            console.log("EditMode entered");
        })
        $(".editMode button").on('click', () => {
            $('.edit').toggle()
            $('.editMode').toggle()
            console.log("EditMode exited");
        })

        $('#editSubmit').on('click', () => {
            $('#editRecordForm').submit()
        })
    })
    </script>
</x-app-layout>

