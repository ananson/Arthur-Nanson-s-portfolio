@extends('layouts.app')

@section('content')
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">Créer une nouvelle dépense</div>

                    <div class="card-body">
                        @if (session('success'))
                            <div class="alert alert-success">
                                {{ session('success') }}
                            </div>
                        @endif

                        <form method="POST" action="{{ route('createExpense', ['group_id' => $group->group_id]) }}">
                            @csrf

                            <div class="form-group">
                                <label for="title">Titre</label>
                                <input type="text" name="title" id="title" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="date">Date</label>
                                <input type="date" name="date" id="date" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="amount">Montant</label>
                                <input type="number" name="amount" id="amount" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="description">Description (facultative)</label>
                                <textarea name="description" id="description" class="form-control"></textarea>
                            </div>

                            <div class="form-group">
                                <label for="to">Membres concernés</label>
                                <select name="to[]" id="to" class="form-control" multiple>
                                    @foreach($group->members as $member)
                                        @if ($member->id !== auth()->user()->id)
                                            <option value="{{ $member->id }}">{{ $member->name }}</option>
                                        @endif
                                    @endforeach
                                </select>
                            </div>

                            <button type="submit" class="btn btn-primary">Créer la dépense</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
