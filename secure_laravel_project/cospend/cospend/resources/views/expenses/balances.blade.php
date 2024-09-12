@extends('layouts.app')

@section('content')
    <div class="container">
        <h2>Soldes des membres du groupe "{{ $group->name }}"</h2>
        <table class="table">
            <thead>
            <tr>
                <th>Membre</th>
                <th>Solde</th>
            </tr>
            </thead>
            <tbody>
            @foreach ($group->members as $member)
                <tr>
                    <td>{{ $member->name }}</td>
                    <td>
                        @if ($balances[$member->id] > 0)
                            <span class="text-success">+{{ $balances[$member->id] }}</span>
                        @elseif ($balances[$member->id] < 0)
                            <span class="text-danger">{{ $balances[$member->id] }}</span>
                        @else
                            <span>{{ $balances[$member->id] }}</span>
                        @endif
                    </td>
                </tr>
            @endforeach
            </tbody>
        </table>
    </div>
@endsection
