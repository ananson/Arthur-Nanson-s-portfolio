@extends('layouts.app')

@section('content')
    <div class="container">
        <h2>Create a new group</h2>
        <form method="POST" action="{{ route('createGroup') }}">
            @csrf
            <div class="form-group">
                <label for="name">Name of group:</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <button type="submit" class="btn btn-primary">Create</button>
        </form>
        @if (session('success'))
            <div class="alert alert-success">
                {{ session('success') }}
            </div>
        @endif
    </div>
@endsection
