@extends('layouts.app')

@section('content')
    <div class="container">
        <h2>Liste des dépenses du groupe "{{ $group->name }}"</h2>
        @if ($expenses)
            <table class="table">
                <thead>
                <tr>
                    <th>Titre</th>
                    <th>Date</th>
                    <th>Créateur</th>
                    <th>Membres concernés</th>
                    <th>Montant</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                @foreach ($expenses as $expense)
                    <tr>
                        <td>{{ $expense->title }}</td>
                        <td>{{ $expense->date }}</td>
                        <td>{{ $expense->createdByUser->name }}</td>

                        <td>{{ $expense->amount }}</td>
                        <td>{{ $expense->description }}</td>
                        <td>
                            @if (auth()->user()->id === $expense->from)
                                <a href="{{ route('showEditExpense', ['expense_id' => $expense->expense_id]) }}" class="btn btn-primary">Mettre à jour</a>
                                <form method="POST" action="{{ route('deleteExpense', ['expense_id' => $expense->expense_id]) }}">
                                    @csrf
                                    @method('DELETE')
                                    <button type="submit" class="btn btn-danger">Supprimer</button>
                                </form>
                            @endif
                        </td>
                    </tr>
                @endforeach
                </tbody>
            </table>
        @else
            <p>Aucune dépense n'a été trouvée pour ce groupe.</p>
        @endif
    </div>
@endsection
