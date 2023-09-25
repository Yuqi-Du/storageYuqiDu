scenario.run({
    'driver':    'http',
    'tags':      'name:"write.*"',
    'cycles':    '10000',
    'threads':   '100',
    'errors':    'timer,warn',
    'docscount': '10000',
    'uri':       'https://us-west-2.aws.data.mongodb-api.com/app/data-sosch/endpoint/data/v1',
    'key':       'wB7HHKnNVYIKbKnVNaNtIzGUsmQqtGcPJZh8Zc9Mksi9arlWJvVdUhY15ElMebLs',
    'workload':  'mongo_vector_crud_dataAPI',
    'alias':     'write',
    'labels':    'workload:$mongo_vector_crud_dataAPI'
});
scenario.run({
    'driver':    'http',
    'tags':      'name:"update.*"',
    'cycles':    '10000',
    'threads':   '100',
    'errors':    'timer,warn',
    'docscount': '10000',
    'uri':       'https://us-west-2.aws.data.mongodb-api.com/app/data-sosch/endpoint/data/v1',
    'key':       'wB7HHKnNVYIKbKnVNaNtIzGUsmQqtGcPJZh8Zc9Mksi9arlWJvVdUhY15ElMebLs',
    'workload':  'mongo_vector_crud_dataAPI',
    'alias':     'update',
    'labels':    'workload:$mongo_vector_crud_dataAPI'
});
